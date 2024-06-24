package com.neoflex.prokhorov.values;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum AccountFilterField {
    SURNAME("surname"),
    NAME("name"),
    PATRONYMIC("patronymic"),
    PHONE("phone"),
    MAIL("mail");

    String value;
}
