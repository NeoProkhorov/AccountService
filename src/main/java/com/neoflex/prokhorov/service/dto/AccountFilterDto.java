package com.neoflex.prokhorov.service.dto;

import lombok.Data;

@Data
public class AccountFilterDto {
    String surname;
    String name;
    String patronymic;
    String phone;
    String mail;
}
