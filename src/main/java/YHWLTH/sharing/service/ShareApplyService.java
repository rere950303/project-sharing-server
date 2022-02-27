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
import java.time.Period;
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

        SharingItem sharingItem = sharingItemRepo.findByShareItemId(shareItem.getId()).orElse(null);
        if (sharingItem != null && (sharingItem.getEndDate().isEqual(shareApplyDTO.getStartDate()) ||
                sharingItem.getEndDate().isAfter(shareApplyDTO.getStartDate()))) {
            throw new IllegalStateException(String.format("해당기간에 이미 공유중인 아이템입니다. %04d-%02d-%02d 이후로 신청해주세요.",
                    sharingItem.getEndDate().getYear(), sharingItem.getEndDate().getMonthValue(), sharingItem.getEndDate().getDayOfMonth()));
        }

        Period period = Period.between(shareApplyDTO.getStartDate(), shareApplyDTO.getEndDate());
        if (user.getPoint() < shareItem.getDeposit() + period.getDays() * shareItem.getRentalFee()) {
            throw new PointShortageEx("최소 (보증금 + 대여기간) 만큼의 포인트가 있어야 합니다. 충전 후 다시 시도해주세요.");
        }

        User itemUser = shareItem.getUser();
        if (itemUser.getId().equals(user.getId())) {
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

        if (shareApply.getEndDate().isBefore(LocalDate.now())) {
            shareApplyRepo.delete(shareApply);
            throw new IllegalStateException("endDate가 만료된 공유신청입니다. 해당 공유신청을 삭제합니다.");
        }

        User user = shareApply.getUser();
        ShareItem shareItem = shareApply.getShareItem();

        if (shareItem.getIsShared()) {
            throw new IllegalStateException("이미 공유중입니다.");
        }

        Period period = Period.between(LocalDate.now(), shareApply.getEndDate());

        if (user.getPoint() < shareItem.getDeposit() + period.getDays() * shareItem.getRentalFee()) {
            throw new PointShortageEx("상대방의 포인트가 부족하여 공유을 수락할 수 없습니다.");
        }

        SharingItem sharingItem = new SharingItem(shareApply, user, shareItem);

        shareApplyRepo.delete(shareApply);
        sharingItemRepo.save(sharingItem);
        shareItem.changeIsShared();
        user.minusPoint(shareItem.getDeposit() + period.getDays() * shareItem.getRentalFee());

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<CommonResult> delete(Long shareApplyId, User user) {
        ShareApply shareApply = shareApplyRepo.findById(shareApplyId).orElse(null);
        if (shareApply == null) {
            throw new IllegalArgumentException("해당되는 id의 공유신청이 없습니다.");
        }

        User owner = shareApply.getUser();
        if (!owner.getId().equals(user.getId())) {
            throw new AccessDeniedException("공유신청 삭제 권한이 없습니다.");
        }

        shareApplyRepo.deleteById(shareApplyId);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CommonResult> complete(Long sharingItemId, User user) {
        SharingItem sharingItem = sharingItemRepo.findById(sharingItemId).orElse(null);

        if (sharingItem == null) {
            throw new IllegalArgumentException("해당되는 id의 공유중인 아이템이 없습니다.");
        }

        ShareItem shareItem = sharingItem.getShareItem();
        if (!shareItem.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("공유 완료 권한이 없습니다.");
        }

        shareItem.changeIsShared();
        sharingItem.getUser().addPoint(sharingItem.getDeposit());

        sharingItemRepo.deleteById(sharingItemId);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }
}