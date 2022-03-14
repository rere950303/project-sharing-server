package YHWLTH.sharing.repo.custom;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.ShareApplyListDTO;
import YHWLTH.sharing.dto.response.SharingItemListDTO;
import org.springframework.data.domain.Page;

public interface ShareApplyCustomRepo {
    Page<ShareApplyListDTO> shareApplyList(PageRequestDTO pageRequestDTO, Long userId);
}
