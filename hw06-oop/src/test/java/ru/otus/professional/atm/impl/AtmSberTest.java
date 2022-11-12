package ru.otus.professional.atm.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.professional.Ioc;
import ru.otus.professional.atm.Atm;
import ru.otus.professional.atm.dto.BanknotePerDenomination;
import ru.otus.professional.atm.dto.MoneyDto;
import ru.otus.professional.exception.AtmException;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AtmSberTest {

    private static final int ERROR_DENOMINATION = 10000;
    private Atm atm;
    private int startSum;

    @BeforeEach
    void setUp() {
        atm = Ioc.createAtm(Set.of(10, 50, 100, 500, 1000));

        atm.acceptBanknotes(new BanknotePerDenomination(10, 5));
        atm.acceptBanknotes(new BanknotePerDenomination(50, 5));
        atm.acceptBanknotes(new BanknotePerDenomination(100, 5));
        atm.acceptBanknotes(new BanknotePerDenomination(1000, 5));

        startSum =
                10 * 5 +
                50 * 5 +
                100 * 5 +
                1000 * 5;
    }

    @Test
    void acceptBanknotes() {
        assertThat(atm.returnBalance())
                .isEqualTo(50 + 250 + 500 + 5000);
    }

    @Test
    void acceptBanknotes_whenDenominatioNotFound() {
        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.acceptBanknotes(new BanknotePerDenomination(ERROR_DENOMINATION, 5)))
                .withMessage("Box with denomination \"" + ERROR_DENOMINATION + "\" not found");
    }

    @Test
    void giveMoney() {
        var retSum = 430;

        var moneyDto = atm.giveMoney(retSum);

        assertThat(moneyDto.getMoneySum()).isEqualTo(retSum);
        assertThat(moneyDto.getMoneyBanknotes())
                .extracting(BanknotePerDenomination::banknotes, BanknotePerDenomination::denomination)
                .contains(
                        tuple(3, 10),
                        tuple(4, 100)
                );

        assertThat(atm.returnBalance())
                .isEqualTo(startSum - retSum);
    }

    @Test
    void giveMoney_whenBanknotesLessThenNeed() {
        var retSum = 630;

        var moneyDto = atm.giveMoney(retSum);

        assertThat(moneyDto.getMoneySum()).isEqualTo(retSum);
        assertThat(moneyDto.getMoneyBanknotes())
                .extracting(BanknotePerDenomination::banknotes, BanknotePerDenomination::denomination)
                .contains(
                        tuple(3, 10),
                        tuple(5, 100),
                        tuple(2, 50)
                );

        assertThat(atm.returnBalance())
                .isEqualTo(startSum - retSum);
    }

    @Test
    void giveMoney_whenMoneyLessThenNeed() {
        var retSum = Integer.MAX_VALUE;

        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.giveMoney(retSum))
                .withMessage("Failed to collect the required amount \"" + retSum + "\"");
    }

    @Test
    void giveMoney_whenBanknotesNotFound() {
        var retSum = 13;

        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.giveMoney(retSum))
                .withMessage("Failed to collect the required amount \"" + retSum + "\"");
    }

    @Test
    void giveMoney_whenNegationValue() {
        var retSum = -13;

        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atm.giveMoney(retSum))
                .withMessage("Atm not working with negative values");
    }

    @Test
    void returnBalance() {
        assertThat(atm.returnBalance())
                .isEqualTo(startSum);
    }
}