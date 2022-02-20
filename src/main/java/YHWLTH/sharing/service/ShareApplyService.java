package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareApplyDTO;
import YHWLTH.sharing.entity.ShareApply;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.SharingItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.ex.PointShortageEx;
import YHWLTH.sharing.repo.ShareApplyRepo;
import YHWLTH.sharing.repo.ShareItemRepo;
import YHWLTH.sharing.repo.SharingItemRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareApplyService {

    private final ShareItemRepo shareItemRepo;
    private final ShareApplyRepo shareApplyRepo;
    private final SharingItemRepo sharingItemRepo;

    public ResponseEntity<CommonResult> apply(ShareApplyDTO shareApplyDTO, User user) {
        if (!(shareApplyDTO.getStartDate().isEqual(shareApplyDTO.getEndDate()) ||
                shareApplyDTO.getStartDate().isBefore(shareApplyDTO.getEndDate()))) {
            throw new IllegalArgumentException("endDate가 startDate보다 이전일 수 없습니다.");
        }

        ShareItem shareItem = shareItemRepo.findById(shareApplyDTO.getShareItemId()).orElse(null);

        if (shareItem == null) {
            throw new IllegalArgumentException("해당되는 id의 아이템이 없습니다.");
        }

        if (user.getPoint() < shareItem.getDeposit()) {
            throw new PointShortageEx("최소 보증금 만큼의 포인트가 있어야 합니다. 충전 후 다시 시도해주세요");
        }

        User itemUser = shareItem.getUser();

        if (itemUser.getUserId().equals(user.getUserId())) {
            throw new IllegalStateException("자신의 아이템에 대해 공유신청 할 수 없습니다.");
        }

        List<ShareApply> shareApplyList = shareApplyRepo.findByUserAndShareItem(user, shareItem);

        if (!shareApplyList.isEmpty()) {
            throw new IllegalStateException("이미 신청한 아이템입니다.");
        }

        ShareApply shareApply = new ShareApply(user, shareItem, shareApplyDTO);
        shareApplyRepo.save(shareApply);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<CommonResult> accept(Long shareApplyId) {
        ShareApply shareApply = shareApplyRepo.findById(shareApplyId).orElse(null);

        if (shareApply == null) {
            throw new IllegalArgumentException("해당되는 id의 공유신청이 없습니다.");
        }

        if (shareApply.getEndTime().isBefore(LocalDate.now())) {
            shareApplyRepo.delete(shareApply);
            throw new IllegalStateException("endDate가 만료된 공유신청입니다. 해당 공유신청을 삭제합니다.");
        }

        User user = shareApply.getUser();
        ShareItem shareItem = shareApply.getShareItem();
        SharingItem sharingItem = new SharingItem(shareApply, user, shareItem);

        shareApplyRepo.delete(shareApply);
        sharingItemRepo.save(sharingItem);
        shareItem.changeIsShared();

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<CommonResult> delete(Long shareApplyId, User user) {
        ShareApply shareApply = shareApplyRepo.findById(shareApplyId).orElse(null);
        if (shareApply == null) {
            throw new IllegalArgumentException("해당되는 id의 공유신청이 없습니다.");
        }

        User owner = shareApply.getUser();
        if (!owner.getUserId().equals(user.getUserId())) {
            throw new AccessDeniedException("공유신청 삭제 권한이 없습니다.");
        }

        shareApplyRepo.deleteById(shareApplyId);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }
}
