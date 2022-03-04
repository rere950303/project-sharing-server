package YHWLTH.sharing.dto.request;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareItemUpdateDTO {

    @NotNull(message = "아이템 id를 입력해주세요.")
    @ApiParam(value = "shareItemId", required = true)
    private Long shareItemId;

    @ApiParam(value = "아이템 상세설명")
    private String desc;

    @Range(max = 10000, message = "[0, 10000] 범위로 입력해주세요.")
    @ApiParam(value = "대여비")
    private Long rentalFee;

    @ApiParam(value = "사진파일")
    private List<MultipartFile> images = new ArrayList<>();

    @ApiParam(value = "kakaoId")
    private String kakaoId;

    @Range(max = 10000, message = "[0, 10000] 범위로 입력해주세요.")
    @ApiParam(value = "보증금")
    private Long deposit;
}