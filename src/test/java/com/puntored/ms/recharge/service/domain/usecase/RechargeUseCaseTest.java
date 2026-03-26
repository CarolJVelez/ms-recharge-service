package com.puntored.ms.recharge.service.domain.usecase;

import com.puntored.ms.recharge.service.application.dto.response.PageDTO;
import com.puntored.ms.recharge.service.domain.exception.BadRequestException;
import com.puntored.ms.recharge.service.domain.model.RechargeModel;
import com.puntored.ms.recharge.service.domain.spi.EventBus;
import com.puntored.ms.recharge.service.domain.spi.IRechargePersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeUseCaseTest {

    @Mock
    private IRechargePersistencePort rechargePersistencePort;

    @Mock
    private EventBus eventBus;

    @InjectMocks
    private RechargeUseCase rechargeUseCase;

    @Test
    void shouldSaveRechargeWithGeneratedIdUserIdAndCreatedAt() {
        RechargeModel input = new RechargeModel();
        input.setPhoneNumber("3172985405");
        input.setAmount(10000);

        when(rechargePersistencePort.saveRecharge(any(RechargeModel.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        RechargeModel response = rechargeUseCase.saveRecharge(input, 2L);

        ArgumentCaptor<RechargeModel> captor = ArgumentCaptor.forClass(RechargeModel.class);
        verify(rechargePersistencePort).saveRecharge(captor.capture());
        RechargeModel sentToPort = captor.getValue();

        assertThat(sentToPort.getId()).isNotBlank();
        assertThat(sentToPort.getUserId()).isEqualTo(2L);
        assertThat(sentToPort.getCreatedAt()).isNotNull();
        assertThat(sentToPort.getPhoneNumber()).isEqualTo("3172985405");
        assertThat(sentToPort.getAmount()).isEqualTo(10000);

        assertThat(response.getId()).isEqualTo(sentToPort.getId());
        assertThat(response.getUserId()).isEqualTo(2L);
        assertThat(response.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldFailAndNotPersistWhenRechargeIsInvalid() {
        RechargeModel input = new RechargeModel();
        input.setPhoneNumber("2172985405");
        input.setAmount(500);

        assertThrows(BadRequestException.class, () -> rechargeUseCase.saveRecharge(input, 2L));

        verify(rechargePersistencePort, never()).saveRecharge(any(RechargeModel.class));
    }

    @Test
    void shouldReturnSamePageWhenPersistenceReturnsData() {
        PageDTO<RechargeModel> page = new PageDTO<>();
        page.setContent(Collections.emptyList());
        page.setPage(0);
        page.setSize(10);
        page.setTotalElements(0);
        page.setTotalPages(0);

        when(rechargePersistencePort.findByAllRecharge(0, 10, 2L)).thenReturn(page);

        PageDTO<RechargeModel> result = rechargeUseCase.findByAllRecharge(2L, 0, 10);

        assertThat(result).isSameAs(page);
    }

    @Test
    void shouldReturnEmptyPageWhenPersistenceReturnsNull() {
        when(rechargePersistencePort.findByAllRecharge(0, 10, 2L)).thenReturn(null);

        PageDTO<RechargeModel> result = rechargeUseCase.findByAllRecharge(2L, 0, 10);

        assertThat(result.getContent()).isEmpty();
        assertThat(result.getPage()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(0);
    }
}
