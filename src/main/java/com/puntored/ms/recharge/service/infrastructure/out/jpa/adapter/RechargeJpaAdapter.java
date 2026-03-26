package com.puntored.ms.recharge.service.infrastructure.out.jpa.adapter;

import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import com.puntored.ms.recharge.service.domain.spi.IRechargePersistencePort;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.entity.RechargeEntity;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper.IRechargeEntityMapper;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.repository.IRechargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class RechargeJpaAdapter implements IRechargePersistencePort {

    private final IRechargeRepository rechargeRepository;
    private final IRechargeEntityMapper rechargeEntityMapper;

    @Override
    public RechargeModel saveRecharge(RechargeModel rechargeModel) {
        RechargeEntity  rechargeEntity = rechargeEntityMapper.toEntity(rechargeModel);
        RechargeEntity saved = rechargeRepository.save(rechargeEntity);
        return rechargeEntityMapper.toModel(saved);
    }

    @Override
    public PageDTO<RechargeModel> findByAllRecharge(int page, int size, Long userIdToken) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RechargeEntity> resultPage = rechargeRepository.findByUserId(userIdToken,pageable);

        List<RechargeModel> content = rechargeEntityMapper.toModelList(resultPage.getContent());

        PageDTO<RechargeModel> dto = new PageDTO<>();
        dto.setContent(content);
        dto.setPage(resultPage.getNumber());
        dto.setSize(resultPage.getSize());
        dto.setTotalElements(resultPage.getTotalElements());
        dto.setTotalPages(resultPage.getTotalPages());

        return dto;
    }
}
