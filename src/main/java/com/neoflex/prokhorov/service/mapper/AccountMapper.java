package com.neoflex.prokhorov.service.mapper;

import com.neoflex.prokhorov.domain.Account;
import com.neoflex.prokhorov.service.dto.AccountDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountDto toDto(Account source);

    Account toEntity(AccountDto source);

    default List<AccountDto> toDtoList(List<Account> accountList) {
        return accountList.stream()
                .map(this::toDto)
                .toList();
    }
}
