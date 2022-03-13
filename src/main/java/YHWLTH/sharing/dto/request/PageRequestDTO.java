package YHWLTH.sharing.dto.request;

import YHWLTH.sharing.entity.ItemType;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @ApiParam(value = "userId", example = "1", defaultValue = "null")
    private Long userId = null;

    @ApiParam(value = "page", example = "1", defaultValue = "1")
    private Integer page = 1;

    @ApiParam(value = "size", example = "10", defaultValue = "10")
    private Integer size = 10;

    @ApiParam(value = "itemType", example = "GOWN", defaultValue = "null")
    private ItemType itemType = null;

    @ApiParam(value = "공유중 여부", example = "True", defaultValue = "null")
    private Boolean isShared = null;

    @ApiParam(value = "원하는 보증금 <= deposit", example = "10000", defaultValue = "null")
    private Long deposit = null;

    @ApiParam(value = "원하는 대여비 <= rentalFee", example = "10000", defaultValue = "null")
    private Long rentalFee = null;

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}