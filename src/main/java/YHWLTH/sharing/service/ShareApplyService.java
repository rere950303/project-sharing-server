package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareApplyDTO;
import YHWLTH.sharing.entity.ShareApply;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ShareApplyRepo;
import YHWLTH.sharing.repo.ShareItemRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShareApplyService {

    private final ShareItemRepo shareItemRepo;
    private final ShareApplyRepo shareApplyRepo;

    public ResponseEntity<CommonResult> apply(ShareApplyDTO shareApplyDTO, User user) {
        if (!(shareApplyDTO.getStartDate().isEqual(shareApplyDTO.getEndDate()) ||
                shareApplyDTO.getStartDate().isBefore(shareApplyDTO.getEndDate()))) {
            throw new IllegalArgumentException("endDate가 startDate보다 이전일 수 없습니다.");
        }

        ShareItem shareItem = shareItemRepo.findById(shareApplyDTO.getShareItemId()).orElse(null);

        if (shareItem == null) {
            throw new IllegalArgumentException("해당되는 id의 아이템이 없습니다.");
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
}
