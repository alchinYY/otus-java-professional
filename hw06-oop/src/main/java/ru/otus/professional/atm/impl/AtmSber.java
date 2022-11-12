package ru.otus.professional.atm.impl;

import ru.otus.professional.atm.Atm;
import ru.otus.professional.atm.MoneyBox;
import ru.otus.professional.atm.dto.MoneyDto;
import ru.otus.professional.atm.dto.BanknotePerDenomination;
import ru.otus.professional.exception.AtmException;

import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public final class AtmSber implements Atm {

    private final SortedMap<Integer, MoneyBox> moneyBoxes;

    public AtmSber(Map<Integer, MoneyBox> moneyBoxes) {
        this.moneyBoxes = new TreeMap<>(Comparator.reverseOrder());
        this.moneyBoxes.putAll(moneyBoxes);
    }

    @Override
    public void acceptBanknotes(BanknotePerDenomination banknotePerDenomination) {
        var banknotes = banknotePerDenomination.banknotes();
        checkNegativeValue(banknotes);
        var moneyBox = getBoxByDenomination(banknotePerDenomination.denomination());
        moneyBox.putBanknotes(banknotes);
    }

    @Override
    public MoneyDto giveMoney(final int amountOfMoney) {
        checkNegativeValue(amountOfMoney);
        var moneyDto = new MoneyDto(amountOfMoney);
        var remainder = amountOfMoney;
        for (var boxEntry : moneyBoxes.entrySet()) {
            var denomination = boxEntry.getKey();
            if(denomination <= remainder) {
                var banknotes = remainder / denomination;
                var retBanknotes = boxEntry.getValue().receiveBanknotes(banknotes);
                remainder = remainder - retBanknotes * denomination;

                if(retBanknotes != 0) {
                    var banknotePerDenominationDto = new BanknotePerDenomination(denomination, retBanknotes);
                    moneyDto.putMoneyBanknotes(banknotePerDenominationDto);
                }
                if(remainder == 0) break;
            }
        }
        checkReaminder(amountOfMoney, remainder);
        return moneyDto;
    }

    @Override
    public int returnBalance() {
        return moneyBoxes.entrySet().stream()
                .map(entry -> entry.getKey() * entry.getValue().getNumberOfBanknotes())
                .mapToInt(b1 -> b1)
                .sum();
    }

    private void checkReaminder(int amountOfMoney, int remainder) {
        if(remainder != 0) {
            throw new AtmException("Failed to collect the required amount \"" + amountOfMoney + "\"");
        }
    }

    private void checkNegativeValue(int value) {
        if (value <= 0) {
            throw new AtmException("Atm not working with negative values");
        }
    }

    private MoneyBox getBoxByDenomination(int denomination) {
        if (!moneyBoxes.containsKey(denomination)) {
            throw new AtmException("Box with denomination \"" + denomination + "\" not found");
        } else {
            return moneyBoxes.get(denomination);
        }
    }

}
