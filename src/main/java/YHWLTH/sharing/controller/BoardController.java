package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.PageResultDTO;
import YHWLTH.sharing.dto.response.ShareItemListDTO;
import YHWLTH.sharing.dto.response.ShareItemReadDTO;
import YHWLTH.sharing.dto.response.SharingItemListDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.ShareItemService;
import YHWLTH.sharing.service.SharingItemService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Api(tags = "BoardController")
@Validated
public class BoardController {

    private final ShareItemService shareItemService;
    private final SharingItemService sharingItemService;

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

    @GetMapping("/image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 불러오기 성공", content = @Content(schema = @Schema(implementation = UrlResource.class))),
            @ApiResponse(responseCode = "400", description = "이미지 불러오기 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "이미지 불러오기", description = "이미지 로딩을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Resource> getImage(@RequestParam @NotBlank String imageName) throws MalformedURLException {
        return shareItemService.getImage(imageName);
    }

    @GetMapping("/shareItemList")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시판 불러오기 성공"),
            @ApiResponse(responseCode = "400", description = "게시판 불러오기 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
    })
    @Operation(summary = "게시판 불러오기", description = "게시판을 불러오는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResultDTO<ShareItemListDTO>> shareItemList(@ModelAttribute PageRequestDTO pageRequestDTO) {
        return shareItemService.shareItemList(pageRequestDTO, null);
    }

    @GetMapping("/shareItemList/{userId}")
    @PreAuthorize(value = "#user.id == #userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "개인 게시판 불러오기 성공"),
            @ApiResponse(responseCode = "400", description = "개인 게시판 불러오기 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "개인 게시판 불러오기 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "개인 게시판 불러오기", description = "개인 게시판을 불러오는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResultDTO<ShareItemListDTO>> shareItemListForUser(
            @ModelAttribute PageRequestDTO pageRequestDTO, @CurrentUser @ApiIgnore User user, @PathVariable Long userId) {
        return shareItemService.shareItemList(pageRequestDTO, userId);
    }

    @GetMapping("/sharingItemList/{userId}")
    @PreAuthorize(value = "#user.id == #userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "빌리고 있는 물품 불러오기 성공"),
            @ApiResponse(responseCode = "400", description = "빌리고 있는 물품 불러오기 실패", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "빌리고 있는 물품 불러오기 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "빌리고 있는 물품 불러오기", description = "빌리고 있는 물품을 불러오는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResultDTO<SharingItemListDTO>> sharingItemListForUser(
            @ModelAttribute PageRequestDTO pageRequestDTO, @CurrentUser @ApiIgnore User user, @PathVariable Long userId) {
        return sharingItemService.sharingItemList(pageRequestDTO, userId);
    }
}