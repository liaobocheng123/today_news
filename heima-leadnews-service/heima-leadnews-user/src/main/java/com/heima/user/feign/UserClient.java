package com.heima.user.feign;

import com.heima.model.user.pojos.ApUser;
import com.heima.user.service.ApUserService;
import com.lbc.apis.user.IUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
@Component
public class UserClient implements IUserClient {
    @Autowired
    private ApUserService apUserService;
    @Override
    @GetMapping("/api/v1/user/{id}")
    public ApUser findUserById(Integer id) {
        return apUserService.getById(id);
    }
}
