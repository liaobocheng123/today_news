package com.lbc.user.controller.v1;

import com.lbc.apis.user.ApUserRealnameControllerApi;
import com.lbc.common.constants.user.UserConstants;
import com.lbc.model.common.dtos.ResponseResult;
import com.lbc.model.user.dtos.AuthDto;
import com.lbc.user.service.ApUserRealnameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class ApUserRealnameController implements ApUserRealnameControllerApi {

    @Autowired
    private ApUserRealnameService userRealnameService;

    @PostMapping("/list")
    @Override
    public ResponseResult loadListByStatus(@RequestBody AuthDto dto){
        return userRealnameService.loadListByStatus(dto);
    }

    /**
     * 审核通过
     * @param dto
     * @return
     */
    @PostMapping("/authPass")
    @Override
    public ResponseResult authPass(@RequestBody AuthDto dto) {
        return userRealnameService.updateStatusById(dto, UserConstants.PASS_AUTH);
    }

    /**
     * 审核失败
     * @param dto
     * @return
     */
    @PostMapping("/authFail")
    @Override
    public ResponseResult authFail(@RequestBody AuthDto dto) {
        return userRealnameService.updateStatusById(dto, UserConstants.FAIL_AUTH);
    }

}