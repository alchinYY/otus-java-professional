package ru.otus.professional.atm.dto;

import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class MoneyDto {

    private final int moneySum;

    private final Set<BanknotePerDenomination> moneyBanknotes = new HashSet<>();

    public void putMoneyBanknotes(BanknotePerDenomination dto) {
        moneyBanknotes.add(dto);
    }

    public Set<BanknotePerDenomination> getMoneyBanknotes() {
        return Collections.unmodifiableSet(moneyBanknotes);
    }

    public int getMoneySum() {
        return moneySum;
    }
}
