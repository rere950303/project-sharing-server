package YHWLTH.sharing.controller;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/shareitem")
@RequiredArgsConstructor
@Api(tags = "ShareItemController")
public class ShareItemController {

    private final ShareItemService shareItemService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "아아템 등록 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "아이템 등록 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 등록", description = "아이템 등록을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> register(@Valid @ModelAttribute ShareItemRegisterDTO registerDTO) throws IOException {
        return shareItemService.register(registerDTO);
    }
}
