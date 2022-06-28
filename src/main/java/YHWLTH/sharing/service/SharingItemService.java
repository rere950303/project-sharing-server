package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.response.PageResultDTO;
import YHWLTH.sharing.dto.response.SharingItemListDTO;
import YHWLTH.sharing.repo.SharingItemRepo;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SharingItemService {

    private final SharingItemRepo sharingItemRepo;

    public ResponseEntity<PageResultDTO<SharingItemListDTO>> sharingItemList(PageRequestDTO pageRequestDTO, Long userId) {
        Page<SharingItemListDTO> page = sharingItemRepo.sharingItemList(pageRequestDTO, userId);
        PageResultDTO<SharingItemListDTO> result = new PageResultDTO<>(page);
        ApiUtil.makeSuccessResult(result, ApiUtil.SUCCESS_OK);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}