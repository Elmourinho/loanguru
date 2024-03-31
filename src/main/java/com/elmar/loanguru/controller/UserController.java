package com.elmar.loanguru.controller;

import com.elmar.loanguru.model.dto.UserDto;
import com.elmar.loanguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/username/{username}")
    public ResponseEntity<UserDto> add(@PathVariable String username) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.add(username));
    }
}
