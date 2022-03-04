package YHWLTH.sharing.dto.request;

import YHWLTH.sharing.entity.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Schema(description = "page", example = "1", defaultValue = "1")
    private int page = 1;

    @Schema(description = "size", example = "10", defaultValue = "10")
    private int size = 10;

    @Schema(description = "itemType", example = "GOWN", defaultValue = "null")
    private ItemType itemType = null;

    @Schema(description = "공유중 여부", example = "True", defaultValue = "null")
    private Boolean isShared = null;

    @Schema(description = "원하는 보증금 <= deposit", example = "10000", defaultValue = "null")
    private Long deposit = null;

    @Schema(description = "원하는 대여비 <= rentalFee", example = "10000", defaultValue = "null")
    private Long rentalFee = null;

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
