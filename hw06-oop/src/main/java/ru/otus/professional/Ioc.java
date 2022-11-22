package ru.otus.professional;

import lombok.experimental.UtilityClass;
import ru.otus.professional.atm.Atm;
import ru.otus.professional.atm.MoneyBox;
import ru.otus.professional.atm.impl.AtmSber;
import ru.otus.professional.atm.impl.MoneyBoxImpl;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class Ioc {

    public Atm createAtm(Set<Integer> denomination) {
        Map<Integer, MoneyBox> boxes = denomination.stream()
                .map(MoneyBoxImpl::new)
                .collect(Collectors.toMap(MoneyBox::getDenomination, b -> b));
        return new AtmSber(boxes);
    }

}
