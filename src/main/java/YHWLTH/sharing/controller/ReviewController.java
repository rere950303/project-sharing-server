package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.request.ReviewUpdateDTO;
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
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
@Api(tags = "ReviewController")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 등록 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 등록 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "리뷰 등록", description = "리뷰 등록을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> register(@Valid @RequestBody ReviewRegisterDTO registerDTO, @ApiIgnore @CurrentUser User user) {
        return reviewService.register(registerDTO, user);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 업데이트 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 업데이트 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "리뷰 업데이트 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))

    })
    @Operation(summary = "리뷰 업데이트", description = "리뷰 업데이트를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> update(@Valid @RequestBody ReviewUpdateDTO updateDTO, @ApiIgnore @CurrentUser User user) {
        return reviewService.update(updateDTO, user);
    }

    @DeleteMapping("/{reviewId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리뷰 삭제 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "리뷰 삭제 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "리뷰 삭제 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))

    })
    @Operation(summary = "리뷰 삭제", description = "리뷰 삭제를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> delete(@PathVariable Long reviewId, @ApiIgnore @CurrentUser User user) {
        return reviewService.delete(reviewId, user);
    }
}