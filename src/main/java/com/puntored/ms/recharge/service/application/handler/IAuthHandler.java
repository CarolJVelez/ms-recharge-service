package com.puntored.ms.recharge.service.application.handler;

import com.puntored.ms.recharge.service.application.dto.request.AuthRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.AuthResponseDto;

public interface IAuthHandler {

    AuthResponseDto login (AuthRequestDto authRequestDto);
}
