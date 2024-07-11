package com.neoflex.prokhorov.domain;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Account extends AbstractPersistable<Long> {
    String bankId;
    String surname;
    String name;
    String patronymic;
    LocalDate birth;
    String passport;
    String birthPlace;
    String phone;
    String mail;
    String registrationAddress;
    String factAddress;
    @NotNull
    String login;
    String password;
}
