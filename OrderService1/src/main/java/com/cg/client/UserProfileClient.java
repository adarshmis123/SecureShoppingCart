package com.cg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cg.DTO.UserProfileDto;

@FeignClient(name = "profileauthentication")
public interface UserProfileClient {

    @GetMapping("/profileController/getByName/{username}")
    UserProfileDto getUserProfileByUsername(@PathVariable("username") String username);
}
