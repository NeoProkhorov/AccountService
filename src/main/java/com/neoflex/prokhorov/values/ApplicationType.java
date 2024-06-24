package com.neoflex.prokhorov.values;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ApplicationType {
    MAIL("mail"),
    MOBILE("mobile"),
    BANK("bank"),
    GOSUSLUGI("gosuslugi");

    String header;

    public static Optional<ApplicationType> getByHeader(String header) {
        return Arrays.stream(ApplicationType.values())
                .filter(type -> Objects.equals(type.header, header))
                .findFirst();
    }
}
