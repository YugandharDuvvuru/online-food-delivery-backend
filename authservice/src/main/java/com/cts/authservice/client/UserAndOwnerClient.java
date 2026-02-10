package com.cts.authservice.client;

import com.cts.authservice.dto.UserRegisterResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="APIGATEWAY")
public interface UserAndOwnerClient {
    @PostMapping("/owner-api/owner/save-details")
    public ResponseEntity<String> saveOwnerDetails(@RequestBody UserRegisterResponseDto userData);
    @PostMapping("user-api/user/save-details")
    public ResponseEntity<String> saveUserDetails(@RequestBody UserRegisterResponseDto userData);
}
