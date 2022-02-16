package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.dto.request.ShareItemUpdateDTO;
import YHWLTH.sharing.dto.response.ShareItemReadDTO;
import YHWLTH.sharing.entity.Image;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ShareItemRepo;
import YHWLTH.sharing.repo.UserRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShareItemService {

    private final ShareItemRepo shareItemRepo;
    private final UserRepo userRepo;
    @Value("${file.path}")
    private String path;

    public ResponseEntity<CommonResult> register(ShareItemRegisterDTO registerDTO) throws IOException {
        User user = userRepo.findById(registerDTO.getUserId()).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("해당되는 사용자가 없습니다.");
        }

        List<Image> images = storeImages(registerDTO.getImages());
        ShareItem shareItem = new ShareItem(registerDTO, images, user);

        user.getShareItemList().add(shareItem);
        images.forEach(image -> image.makeMappingShareItem(shareItem));
        shareItemRepo.save(shareItem);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    public List<Image> storeImages(List<MultipartFile> images) throws IOException {
        ArrayList<Image> result = new ArrayList<>();

        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                result.add(storeImage(image));
            }
        }

        return result;
    }

    private Image storeImage(MultipartFile image) throws IOException {
        if (!image.getContentType().startsWith("image")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 할수 있습니다.");
        }

        String originalFileName = URLDecoder.decode(image.getOriginalFilename(), StandardCharsets.UTF_8);
        String storedFileName = getStoredFileName(originalFileName);

        try {
            image.transferTo(new File(path + storedFileName));
        } catch (IOException e) {
            log.info("[ShareItemService][storeImage]IOException");
            throw new IOException("사진 저장에 실패했습니다. 다시 시도해주세요.");
        }

        return new Image(originalFileName, storedFileName);
    }

    private String getStoredFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "_" + originalFileName;
    }

    @Transactional
    public ResponseEntity<CommonResult> update(ShareItemUpdateDTO updateDTO, User user) throws IOException {
        ShareItem shareItem = shareItemRepo.findById(updateDTO.getShareItemId()).orElse(null);

        if (shareItem == null) {
            throw new IllegalArgumentException("해당되는 id의 아이템이 없습니다.");
        }

        if (!shareItem.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("업데이트 권한이 없습니다.");
        }

        List<Image> images = storeImages(updateDTO.getImages());

        shareItem.update(updateDTO, images);
        images.forEach(image -> image.makeMappingShareItem(shareItem));

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }

    public ResponseEntity<CommonResult> removeImage(String imageName) {
        imageName = URLDecoder.decode(imageName, StandardCharsets.UTF_8);
        File file = new File(path + imageName);
        boolean result = file.delete();

        return result ? new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK) :
                new ResponseEntity<>(ApiUtil.getFailResult(null, ApiUtil.INTERNAL_SERVER_ERROR,
                        "사진 삭제에 실패했습니다. 다시 시도해 주세요."), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Transactional
    public ResponseEntity<CommonResult> remove(Long shareItemId, User user) {
        ShareItem shareItem = shareItemRepo.findById(shareItemId).orElse(null);

        if (shareItem == null) {
            throw new IllegalArgumentException("해당되는 id의 아이템이 없습니다.");
        }

        if (!shareItem.getUser().getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("삭제 권한이 없습니다.");
        }

        shareItemRepo.delete(shareItem);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }

    public ResponseEntity<ShareItemReadDTO> read(Long shareItemId) {
        ShareItem shareItem = shareItemRepo.findById(shareItemId).orElse(null);

        if (shareItem == null) {
            throw new IllegalArgumentException("해당되는 id의 아이템이 없습니다.");
        }

        User user = shareItem.getUser();
        ShareItemReadDTO shareItemReadDTO = new ShareItemReadDTO(shareItem, user);
        ApiUtil.makeSuccessResult(shareItemReadDTO, ApiUtil.SUCCESS_OK);

        return new ResponseEntity<>(shareItemReadDTO, HttpStatus.OK);
    }

    public ResponseEntity<Resource> getImage(String imageName) throws MalformedURLException {
        imageName = URLDecoder.decode(imageName, StandardCharsets.UTF_8);
        File file = new File(path + imageName);

        try {
            return new ResponseEntity<>(new UrlResource("file:" + file), HttpStatus.OK);
        } catch (MalformedURLException e) {
            log.info("[ShareItemService][getImage]MalformedURLException");
            throw new MalformedURLException("잘못된 URL 형식입니다. 다시 시도해주세요.");
        }
    }
}