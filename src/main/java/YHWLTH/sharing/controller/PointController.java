package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.PointService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/point")
@RequiredArgsConstructor
@Api(tags = "PointController")
@Validated
public class PointController {

    private final PointService pointService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "포인트 충전 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "포인트 충전 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "포인트 충전", description = "포인트 충전을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CommonResult> addPoint(@NotNull @RequestParam Long point, @ApiIgnore @CurrentUser User user) {
        return pointService.addPoint(point, user);
    }

}