package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.response.ReviewScoreDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/review")
@RequiredArgsConstructor
@Api(tags = "ReviewController")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize(value = "#registerDTO.userId != #user.id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 등록 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "리뷰 등록 권한 없음(자기 자신에 대한 리뷰)", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "리뷰 등록", description = "리뷰 등록을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> register(@Valid @RequestBody ReviewRegisterDTO registerDTO, @ApiIgnore @CurrentUser User user) {
        return reviewService.register(registerDTO);
    }

    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰점수 조회 성공", content = @Content(schema = @Schema(implementation = ReviewScoreDTO.class))),
            @ApiResponse(responseCode = "400", description = "리뷰점수 조회 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "리뷰점수 조회", description = "리뷰점수를 조회하는 메소드")
    public ResponseEntity<ReviewScoreDTO> getReviewScore(@PathVariable Long userId) {
        return reviewService.getReviewScore(userId);
    }

}