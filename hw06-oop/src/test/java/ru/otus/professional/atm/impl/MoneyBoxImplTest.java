package ru.otus.professional.atm.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.professional.atm.MoneyBox;

import static org.assertj.core.api.Assertions.*;

class MoneyBoxImplTest {

    private static final int TEST_DENOMINATION = 1000;
    private static final int TEST_NUMBER_OF_BANKNOTES = 5;

    private MoneyBox moneyBox;


    @BeforeEach
    void setUp() {
        moneyBox = new MoneyBoxImpl(TEST_DENOMINATION);
    }

    @Test
    void getDenomination() {
        assertThat(moneyBox.getDenomination()).isEqualTo(TEST_DENOMINATION);
    }

    @Test
    void putBanknotes() {
        moneyBox.putBanknotes(TEST_NUMBER_OF_BANKNOTES);
        assertThat(moneyBox.getNumberOfBanknotes()).isEqualTo(TEST_NUMBER_OF_BANKNOTES);
    }

    @Test
    void receiveBanknotes() {
        moneyBox.putBanknotes(TEST_NUMBER_OF_BANKNOTES);

        assertThat(moneyBox.receiveBanknotes(TEST_NUMBER_OF_BANKNOTES)).isEqualTo(TEST_NUMBER_OF_BANKNOTES);
        assertThat(moneyBox.getNumberOfBanknotes()).isZero();
    }

    @Test
    void receiveBanknotes_whenBanknotesLessThenNeed() {
        moneyBox.putBanknotes(TEST_NUMBER_OF_BANKNOTES);

        assertThat(moneyBox.receiveBanknotes(TEST_NUMBER_OF_BANKNOTES + TEST_NUMBER_OF_BANKNOTES))
                .isEqualTo(TEST_NUMBER_OF_BANKNOTES);
        assertThat(moneyBox.getNumberOfBanknotes()).isZero();
    }

    @Test
    void getNumberOfBanknotes() {
        moneyBox.putBanknotes(TEST_NUMBER_OF_BANKNOTES);
        assertThat(moneyBox.getNumberOfBanknotes()).isEqualTo(TEST_NUMBER_OF_BANKNOTES);
    }
}