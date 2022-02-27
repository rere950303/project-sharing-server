package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.request.ReviewUpdateDTO;
import YHWLTH.sharing.entity.Review;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ReviewRepo;
import YHWLTH.sharing.repo.UserRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepo reviewRepo;
    private final UserRepo userRepo;

    public ResponseEntity<CommonResult> register(ReviewRegisterDTO registerDTO, User user) {
        User target = userRepo.findById(registerDTO.getUserId()).orElse(null);
        if (target == null) {
            throw new UsernameNotFoundException("해당되는 사용자가 없습니다.");
        }

        Review review = new Review(registerDTO, target, user);
        reviewRepo.save(review);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<CommonResult> update(ReviewUpdateDTO updateDTO, User user) {
        Review review = reviewRepo.findById(updateDTO.getReviewId()).orElse(null);
        if (review == null) {
            throw new IllegalArgumentException("해당되는 id의 리뷰가 없습니다.");
        }
        if (!review.getWriterId().equals(user.getId())) {
            throw new AccessDeniedException("리뷰 업데이트 권한이 없습니다.");
        }

        review.update(updateDTO);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<CommonResult> delete(Long reviewId, User user) {
        Review review = reviewRepo.findById(reviewId).orElse(null);
        if (review == null) {
            throw new IllegalArgumentException("해당되는 id의 리뷰가 없습니다.");
        }
        if (!review.getWriterId().equals(user.getId())) {
            throw new AccessDeniedException("리뷰 삭제 권한이 없습니다.");
        }

        reviewRepo.deleteById(reviewId);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_OK), HttpStatus.OK);
    }
}
