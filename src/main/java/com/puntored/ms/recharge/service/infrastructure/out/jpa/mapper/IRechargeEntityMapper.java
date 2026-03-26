package com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper;

import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.entity.RechargeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IRechargeEntityMapper {

    RechargeEntity toEntity(RechargeModel rechargeModel);
    RechargeModel toModel (RechargeEntity rechargeEntity);

    List<RechargeModel> toModelList(List<RechargeEntity> rechargeEntities);
}
