package com.puntored.ms.recharge.service.infrastructure.input.rest;


import com.puntored.ms.recharge.service.application.dto.request.AuthRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.AuthResponseDto;
import com.puntored.ms.recharge.service.application.handler.IAuthHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth/login")
@RequiredArgsConstructor
public class AuthRestController {

    private final IAuthHandler authHandler;

    @Operation(summary = "Login a user",
               security = {})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successfully", content = @Content),
            @ApiResponse(responseCode = "409", description = "Login with problems", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AuthResponseDto> saveUser(@Valid @RequestBody AuthRequestDto authRequestDto) {
        AuthResponseDto authResponseDto = authHandler.login(authRequestDto);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }
}
