package com.puntored.ms.recharge.service.infrastructure.input.rest;


import com.puntored.ms.recharge.service.application.dto.request.RechargeRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.application.dto.response.RechargeResponseDto;
import com.puntored.ms.recharge.service.application.handler.IRechargeHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/recharges")
@RequiredArgsConstructor
public class RechargeRestController {

    private final IRechargeHandler rechargeHandler;

    @Operation(summary = "Recharge mobile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recharge successfully", content = @Content),
            @ApiResponse(responseCode = "409", description = "Recharge with problems", content = @Content)
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/buy")
    public ResponseEntity<RechargeResponseDto> saveRecharge(@Valid @RequestBody RechargeRequestDto rechargeRequestDto) {
        RechargeResponseDto rechargeResponseDto = rechargeHandler.saveRecharge(rechargeRequestDto);
        return new ResponseEntity<>(rechargeResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "List of Recharge mobile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Paged list of recharge", content = @Content),
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/history")
    public ResponseEntity<PageDTO<RechargeResponseDto>> listRecharge(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageDTO<RechargeResponseDto> response =
                rechargeHandler.findByAllRecharge(page, size);

        return ResponseEntity.ok(response);
    }
}
