package YHWLTH.sharing.repo.custom;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.ShareItemListDTO;
import org.springframework.data.domain.Page;

public interface ShareItemCustomRepo {

    Page<ShareItemListDTO> shareItemList(PageRequestDTO pageRequestDTO);
}
