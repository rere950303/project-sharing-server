package YHWLTH.sharing.dto.request;

import YHWLTH.sharing.entity.ItemType;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareItemRegisterDTO {

    @NotNull(message = "회원 id를 입력해주세요.")
    @ApiParam(value = "userId", required = true)
    private Long userId;

    @NotNull(message = "아이템 유형을 입력해주세요.")
    @ApiParam(value = "itemType", required = true)
    private ItemType itemType;

    @NotBlank(message = "아이템 설명을 입력해주세요.")
    @ApiParam(value = "아이템 상세설명", required = true)
    private String desc;

    @NotNull(message = "대여비를 입력해주세요.")
    @ApiParam(value = "대여비", required = true)
    private Long rentalFee;

    @ApiParam(value = "사진파일")
    private List<MultipartFile> images = new ArrayList<>();

    @NotBlank(message = "카카오톡 아이디를 입력해주세요.")
    @ApiParam(value = "kakaoId", required = true)
    private String kakaoId;
}