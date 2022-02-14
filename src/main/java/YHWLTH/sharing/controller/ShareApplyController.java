package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareApplyDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.ShareApplyService;
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
@RequestMapping("/api/apply")
@RequiredArgsConstructor
@Api(tags = "ShareApplyController")
public class ShareApplyController {

    private final ShareApplyService shareApplyService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "아아템 공유신청 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "아이템 공유신청 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 공유신청", description = "아이템 공유신청을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> update(@Valid @RequestBody ShareApplyDTO shareApplyDTO, @ApiIgnore @CurrentUser User user) {
        return shareApplyService.apply(shareApplyDTO, user);
    }
}
