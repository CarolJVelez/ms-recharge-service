package com.puntored.ms.recharge.service.infrastructure.out.jpa.mapper;

import com.puntored.ms.recharge.service.domain.model.UserModel;
import com.puntored.ms.recharge.service.infrastructure.out.jpa.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserEntityMapper {

    UserEntity toEntity(UserModel userModel);
    UserModel toModel (UserEntity userEntity);
}
