package com.puntored.ms.recharge.service.infrastructure.out.jpa.repository;

import com.puntored.ms.recharge.service.infrastructure.out.jpa.entity.RechargeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRechargeRepository extends JpaRepository<RechargeEntity, String> {

    @Query("SELECT d FROM RechargeEntity d " +
            "WHERE d.userId = :userId " )
    Page<RechargeEntity> findByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );
}
