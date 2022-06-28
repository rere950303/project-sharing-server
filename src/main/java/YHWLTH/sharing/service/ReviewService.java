package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.response.ReviewScoreDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.UserRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepo userRepo;

    public ResponseEntity<CommonResult> register(ReviewRegisterDTO registerDTO) {
        User user = userRepo.findById(registerDTO.getUserId()).orElseThrow(() -> new UsernameNotFoundException("해당되는 사용자가 없습니다."));
        user.registerReview(registerDTO);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }

    public ResponseEntity<ReviewScoreDTO> getReviewScore(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("해당되는 사용자가 없습니다."));
        ReviewScoreDTO reviewScoreDTO = new ReviewScoreDTO((int) (user.getLenderScore() / user.getLenderReviewNum()),
                (int) (user.getBorrowerScore() / user.getBorrowerReviewNum()));
        ApiUtil.makeSuccessResult(reviewScoreDTO, ApiUtil.SUCCESS_OK);

        return new ResponseEntity<>(reviewScoreDTO, HttpStatus.OK);
    }
}