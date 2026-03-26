package com.puntored.ms.recharge.service.application.mapper;

import com.puntored.ms.recharge.service.application.dto.request.RechargeRequestDto;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRechargeRequestMapper {
    RechargeModel toModel (RechargeRequestDto rechargeRequestDto);
}
