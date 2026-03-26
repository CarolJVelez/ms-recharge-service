package com.puntored.ms.recharge.service.domain.spi;

import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;

public interface IRechargePersistencePort {

    RechargeModel saveRecharge(RechargeModel rechargeModel);
    PageDTO<RechargeModel> findByAllRecharge(int page, int size, Long userIdToken);
}
