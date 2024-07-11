package com.neoflex.prokhorov.service.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDto {
    Long id;
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
    String login;
    String password;
}
