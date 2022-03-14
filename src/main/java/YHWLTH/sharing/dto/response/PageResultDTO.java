package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResultDTO<DTO> extends CommonResult {

    @Schema(description = "데이터")
    private List<DTO> dtoList;

    @Schema(description = "전체 페이지수", example = "7")
    private int totalPage;

    @Schema(description = "현재 페이지", example = "1")
    private int page;

    @Schema(description = "size", example = "10", defaultValue = "10")
    private int size;

    @Schema(description = "시작 페이지", example = "1")
    private int start;

    @Schema(description = "마지막 페이지", example = "9")
    private int end;

    @Schema(description = "이전 페이지 존재 여부", example = "false")
    private boolean prev;

    @Schema(description = "다음 페이지 존재 여부", example = "false")
    private boolean next;

    @Schema(description = "pageList", example = "{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}")
    private List<Integer> pageList;

    public PageResultDTO(Page result) {
        totalPage = result.getTotalPages();
        this.dtoList = result.getContent();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();
        int temEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = temEnd - 9;
        prev = start > 1;
        end = totalPage > temEnd ? temEnd : totalPage;
        next = totalPage > temEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
