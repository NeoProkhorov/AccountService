package com.neoflex.prokhorov.service;

import com.neoflex.prokhorov.domain.Account;
import com.neoflex.prokhorov.domain.AccountRepository;
import com.neoflex.prokhorov.domain.AccountSpecification;
import com.neoflex.prokhorov.service.dto.AccountDto;
import com.neoflex.prokhorov.service.dto.AccountFilterDto;
import com.neoflex.prokhorov.service.mapper.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountService {
    AccountRepository accountRepository;
    AccountMapper accountMapper;
    AccountSpecification accountSpecification;

    public List<AccountDto> getAll() {
        return accountMapper.toDtoList(accountRepository.findAll());
    }

    public AccountDto getById(Long id) {
        return accountMapper.toDto(findById(id));
    }

    private Account findById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден аккаунт с id %s", id)));
    }

    public AccountDto create(AccountDto dto) {
        Account saved = accountRepository.save(accountMapper.toEntity(dto));
        return accountMapper.toDto(saved);
    }

    public List<AccountDto> getAllByFilter(AccountFilterDto dto) {
        List<Account> accounts = accountRepository.findAll(accountSpecification.getAccounts(dto));
        return accountMapper.toDtoList(accounts);
    }

    public AccountDto findByLogin(String login) {
        Account account = accountRepository.findByLogin(login)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Не найден аккаунт с логином %s", login)));
        return accountMapper.toDto(account);
    }
}
