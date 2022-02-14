package YHWLTH.sharing.controller;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.response.ShareItemReadDTO;
import YHWLTH.sharing.service.ShareItemService;
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

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Api(tags = "BoardController")
public class BoardController {

    private final ShareItemService shareItemService;

    @GetMapping("/{shareItemId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "아이템 상세보기 성공", content = @Content(schema = @Schema(implementation = ShareItemReadDTO.class))),
            @ApiResponse(responseCode = "400", description = "아이템 상세보기 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "아이템 상세보기", description = "아이템 상세보기를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ShareItemReadDTO> read(@PathVariable Long shareItemId) {
        return shareItemService.read(shareItemId);
    }

}