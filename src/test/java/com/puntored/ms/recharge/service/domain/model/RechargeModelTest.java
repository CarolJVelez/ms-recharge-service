package com.puntored.ms.recharge.service.domain.model;

import com.puntored.ms.recharge.service.domain.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RechargeModelTest {

    @Test
    void shouldValidateWhenPhoneAndAmountAreValid() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("3172985405");
        model.setAmount(10000);

        assertDoesNotThrow(model::validate);
    }

    @Test
    void shouldFailWhenPhoneIsNull() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber(null);
        model.setAmount(10000);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldFailWhenPhoneDoesNotStartWithThree() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("2172985405");
        model.setAmount(10000);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldFailWhenPhoneLengthIsDifferentFromTen() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("317298540");
        model.setAmount(10000);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldFailWhenPhoneContainsLetters() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("31729854a5");
        model.setAmount(10000);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldFailWhenAmountIsLessThanMinimum() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("3172985405");
        model.setAmount(999);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldFailWhenAmountIsGreaterThanMaximum() {
        RechargeModel model = new RechargeModel();
        model.setPhoneNumber("3172985405");
        model.setAmount(100001);

        assertThrows(BadRequestException.class, model::validate);
    }

    @Test
    void shouldAcceptBoundaryAmounts() {
        RechargeModel minModel = new RechargeModel();
        minModel.setPhoneNumber("3172985405");
        minModel.setAmount(1000);

        RechargeModel maxModel = new RechargeModel();
        maxModel.setPhoneNumber("3172985405");
        maxModel.setAmount(100000);

        assertDoesNotThrow(minModel::validate);
        assertDoesNotThrow(maxModel::validate);
    }
}
