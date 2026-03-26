package com.puntored.ms.recharge.service.domain.api;

import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;

public interface IRechargeServicePort {

    RechargeModel saveRecharge(RechargeModel rechargeModel, Long userIdToken);
    PageDTO<RechargeModel> findByAllRecharge(Long userIdToken, int page, int size);
}
