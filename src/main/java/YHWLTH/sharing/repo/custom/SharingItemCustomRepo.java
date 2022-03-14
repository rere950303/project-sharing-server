package YHWLTH.sharing.repo.custom;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.SharingItemListDTO;
import org.springframework.data.domain.Page;

public interface SharingItemCustomRepo {

    Page<SharingItemListDTO> sharingItemList(PageRequestDTO pageRequestDTO, Long userId);
}
