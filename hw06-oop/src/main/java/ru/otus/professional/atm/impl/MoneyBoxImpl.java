package ru.otus.professional.atm.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.professional.atm.MoneyBox;

@RequiredArgsConstructor
public final class MoneyBoxImpl implements MoneyBox {

    private final int denomination;
    private int balance = 0;

    @Override
    public int getDenomination() {
        return denomination;
    }

    @Override
    public void putBanknotes(int banknotes) {
        balance += banknotes;
    }

    @Override
    public int receiveBanknotes(int banknotes) {
        if(balance < banknotes) {
            var temp = balance;
            balance = 0;
            return temp;
        }
        balance -= banknotes;
        return banknotes;
    }

    @Override
    public int getNumberOfBanknotes() {
        return balance;
    }
}
