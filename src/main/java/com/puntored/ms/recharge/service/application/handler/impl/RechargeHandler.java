package com.puntored.ms.recharge.service.application.handler.impl;

import com.puntored.ms.recharge.service.application.dto.request.RechargeRequestDto;
import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.application.dto.response.RechargeResponseDto;
import com.puntored.ms.recharge.service.application.handler.IRechargeHandler;
import com.puntored.ms.recharge.service.application.mapper.IRechargeRequestMapper;
import com.puntored.ms.recharge.service.application.mapper.IRechargeResponseMapper;
import com.puntored.ms.recharge.service.domain.api.IRechargeServicePort;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class RechargeHandler implements IRechargeHandler {

    private final IRechargeServicePort rechargeServicePort;
    private final IRechargeRequestMapper rechargeRequestMapper;
    private final IRechargeResponseMapper rechargeResponseMapper;


    @Override
    public RechargeResponseDto saveRecharge(RechargeRequestDto rechargeRequestDto) {
        Long userIdToken = userIdToken();
        RechargeModel rechargeModel = rechargeRequestMapper.toModel(rechargeRequestDto);
        RechargeModel saveRecharge = rechargeServicePort.saveRecharge(rechargeModel,userIdToken);
        return rechargeResponseMapper.toResponse(saveRecharge);
    }

    @Override
    public PageDTO<RechargeResponseDto> findByAllRecharge(int page, int size) {
        Long userIdToken = userIdToken();
        PageDTO<RechargeModel> rechargePage = rechargeServicePort.findByAllRecharge(userIdToken,page,size);
        List<RechargeResponseDto> dtoList = rechargeResponseMapper.toListResponseList(rechargePage.getContent());

        PageDTO<RechargeResponseDto> dtoPage = new PageDTO<>();
        dtoPage.setContent(dtoList);
        dtoPage.setPage(rechargePage.getPage());
        dtoPage.setSize(rechargePage.getSize());
        dtoPage.setTotalElements(rechargePage.getTotalElements());
        dtoPage.setTotalPages(rechargePage.getTotalPages());
        return dtoPage;
    }

    private Map<String, Object> getTokenDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("Usuario no autenticado");
        }
        Object details = auth.getDetails();
        if (!(details instanceof Map)) {
            throw new IllegalStateException("Detalles de autenticación no son del tipo Map.");
        }
        return (Map<String, Object>) details;
    }

    public Long userIdToken() {
        Map<String, Object> details = getTokenDetails();
        return (Long) details.get("userId");
    }
}
