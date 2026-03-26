package com.puntored.ms.recharge.service.domain.usecase;

import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.domain.api.IRechargeServicePort;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import com.puntored.ms.recharge.service.domain.model.RechargeSucceededEvent;
import com.puntored.ms.recharge.service.domain.spi.EventBus;
import com.puntored.ms.recharge.service.domain.spi.IRechargePersistencePort;

import java.time.LocalDateTime;
import java.util.UUID;

public class RechargeUseCase implements IRechargeServicePort {

    private final IRechargePersistencePort rechargePersistencePort;
    private final EventBus eventBus;

    public RechargeUseCase(IRechargePersistencePort rechargePersistencePort,
                           EventBus eventBus) {
        this.rechargePersistencePort = rechargePersistencePort;
        this.eventBus = eventBus;
    }

    @Override
    public RechargeModel saveRecharge(RechargeModel rechargeModel, Long userIdToken) {
        rechargeModel.validate();
        rechargeModel.setId(UUID.randomUUID().toString());
        rechargeModel.setCreatedAt(LocalDateTime.now());
        rechargeModel.setUserId(userIdToken);
        RechargeModel saved = rechargePersistencePort.saveRecharge(rechargeModel);

        RechargeSucceededEvent event = new RechargeSucceededEvent(
                saved.getId(),
                saved.getUserId(),
                saved.getAmount(),
                saved.getCreatedAt()
        );

        System.out.println("Publicando evento RechargeSucceededEvent: {}"+ event);

        eventBus.publish(event);

        return saved;
    }

    @Override
    public PageDTO<RechargeModel> findByAllRecharge(Long userIdToken, int page, int size) {
        PageDTO<RechargeModel> orderPage = rechargePersistencePort.findByAllRecharge(page,size,userIdToken);

        if (orderPage == null) {
            PageDTO<RechargeModel> empty = new PageDTO<>();
            empty.setPage(page);
            empty.setSize(size);
            empty.setTotalElements(0);
            empty.setTotalPages(0);
            return empty;
        }

        return orderPage;
    }
}
