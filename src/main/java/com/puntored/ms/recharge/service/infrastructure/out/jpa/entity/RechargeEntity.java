package com.puntored.ms.recharge.service.infrastructure.out.jpa.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "recharge")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RechargeEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "user_id", nullable = false, length = 50)
    private long userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
