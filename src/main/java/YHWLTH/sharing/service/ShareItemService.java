package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.entity.Image;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ShareItemRepo;
import YHWLTH.sharing.repo.UserRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        Optional<User> user = userRepo.findById(registerDTO.getUserId());

        if (user.orElse(null) == null) {
            throw new UsernameNotFoundException("해당되는 사용자가 없습니다.");
        }

        List<Image> images = storeImages(registerDTO.getImages());
        ShareItem shareItem = new ShareItem(registerDTO, images, user.orElse(null));

        createMapping(shareItem, user.orElse(null), images);

        shareItemRepo.save(shareItem);
        CommonResult successResult = ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED);
        return new ResponseEntity<>(successResult, HttpStatus.CREATED);
    }

    private void createMapping(ShareItem shareItem, User user, List<Image> images) {
        user.getShareItemList().add(shareItem);
        images.forEach(image -> image.makeMappingShareItem(shareItem));
    }

    private List<Image> storeImages(List<MultipartFile> images) throws IOException {
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
}