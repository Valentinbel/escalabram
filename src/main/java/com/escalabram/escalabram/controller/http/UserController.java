package com.escalabram.escalabram.controller.http;

import com.escalabram.escalabram.controller.http.request.UpdateLanguageRequest;
import com.escalabram.escalabram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    //endpoints about user itself, not userAuthentication

    @GetMapping("/user/{userId}/language")
    public ResponseEntity<Long> getLanguageId(@PathVariable Long userId) {
        try {
            Long languageId = userService.findLanguageIdByUserId(userId);
            return org.springframework.http.ResponseEntity.status(HttpStatus.OK).body(languageId);
        } catch (Exception e) {
            String message = "Could not find LanguageId for userId: " + userId;
            log.error(message,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }

    @PatchMapping("/user/{userId}/language")
    public ResponseEntity<Integer> updateUserLanguage(@PathVariable Long userId, @RequestBody UpdateLanguageRequest languageIdRequest) {
        try {
            int updateResult = userService.updateLanguageIdById(userId, languageIdRequest.languageId());
            return org.springframework.http.ResponseEntity.status(HttpStatus.OK).body(updateResult);
        } catch (Exception e) {
            String message = "Could not update user Language for userId: " + userId + ", and languageId: " + languageIdRequest.languageId();
            log.error(message,e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
        }
    }
}
