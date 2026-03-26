package com.puntored.ms.recharge.service.application.mapper;

import com.puntored.ms.recharge.service.application.dto.response.RechargeResponseDto;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRechargeResponseMapper {

    RechargeResponseDto toResponse (RechargeModel rechargeModel);
    List<RechargeResponseDto> toListResponseList(List<RechargeModel> rechargeModels);
}
