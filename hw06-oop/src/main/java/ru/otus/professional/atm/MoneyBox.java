package ru.otus.professional.atm;

public interface MoneyBox {

    int getDenomination();

    void putBanknotes(int banknotes);

    int receiveBanknotes(int banknotes);

    int getNumberOfBanknotes();
}
