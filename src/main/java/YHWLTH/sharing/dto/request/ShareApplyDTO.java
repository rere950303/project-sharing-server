package YHWLTH.sharing.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareApplyDTO {

    @Schema(description = "shareItemId", example = "1", required = true)
    @NotNull(message = "아이템 id를 입력해주세요.")
    private Long shareItemId;

    @Schema(description = "startDate", example = "2022-02-13", required = true, allowableValues = {"신청하는 날짜 이후로 입력해주세요."})
    @FutureOrPresent(message = "오늘 날짜 이후로 입력해주세요.")
    @NotNull(message = "startDate를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @Schema(description = "endDate", example = "2022-02-15", required = true, allowableValues = {"신청하는 날짜 이후로 입력해주세요.", "startDate <= endDate"})
    @FutureOrPresent(message = "오늘 날짜 이후로 입력해주세요.")
    @NotNull(message = "endDate를 입력해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
}