package com.puntored.ms.recharge.service.application.handler;

import com.puntored.ms.recharge.service.application.dto.request.RechargeRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.application.dto.response.RechargeResponseDto;

public interface IRechargeHandler {

    RechargeResponseDto saveRecharge (RechargeRequestDto rechargeRequestDto);
    PageDTO<RechargeResponseDto> findByAllRecharge(int page, int size);
}
