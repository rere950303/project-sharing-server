package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.dto.request.ShareItemUpdateDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.ShareItemService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
@RequestMapping("/api/shareItem")
@RequiredArgsConstructor
@Api(tags = "ShareItemController")
@Validated
public class ShareItemController {

    private final ShareItemService shareItemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "아아템 등록 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "아이템 등록 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "500", description = "사진 등록 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 등록", description = "아이템 등록을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> register(@Valid @ModelAttribute ShareItemRegisterDTO registerDTO) throws IOException {
        return shareItemService.register(registerDTO);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아아템 업데이트 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "아이템 업데이트 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 업데이트", description = "아이템 업데이트를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> update(@Valid @ModelAttribute ShareItemUpdateDTO updateDTO) throws IOException {
        return shareItemService.update(updateDTO);
    }

    @DeleteMapping("/image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 삭제 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "이미지 삭제 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "이미지 삭제", description = "이미지 삭제를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> deleteImage(@RequestParam @NotBlank String imageName) {
        return shareItemService.removeImage(imageName);
    }

    @DeleteMapping("/{shareItemId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이템 삭제 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "아이템 삭제 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "아이템 삭제 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 삭제", description = "아이템 삭제를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> delete(@PathVariable Long shareItemId, @ApiIgnore @CurrentUser User user) {
        return shareItemService.remove(shareItemId, user);
    }
}