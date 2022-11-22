package ru.otus.professional.atm;

import ru.otus.professional.atm.dto.MoneyDto;
import ru.otus.professional.atm.dto.BanknotePerDenomination;

public interface Atm {

    void acceptBanknotes(BanknotePerDenomination moneyDto);

    MoneyDto giveMoney(int amountOfMoney);

    int returnBalance();
}
