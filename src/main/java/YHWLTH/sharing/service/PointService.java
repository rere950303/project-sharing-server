package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.util.ApiUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

    @Transactional
    public ResponseEntity<CommonResult> addPoint(Long point, User user) {
        user.addPoint(point);

        return new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED);
    }
}
