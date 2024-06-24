package com.neoflex.prokhorov.service;

import com.neoflex.prokhorov.domain.Account;
import com.neoflex.prokhorov.domain.AccountRepository;
import com.neoflex.prokhorov.domain.AccountSpecification;
import com.neoflex.prokhorov.service.dto.AccountDto;
import com.neoflex.prokhorov.service.dto.AccountFilterDto;
import com.neoflex.prokhorov.service.mapper.AccountMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @InjectMocks
    AccountService accountService;
    @Mock
    AccountRepository accountRepository;
    @Mock
    AccountMapper accountMapper;
    @Mock
    AccountSpecification accountSpecification;

    @Test
    void getAll() {
        when(accountRepository.findAll()).thenReturn(new ArrayList<>());
        when(accountMapper.toDtoList(anyList())).thenReturn(List.of(new AccountDto()));
        assertEquals(1, accountService.getAll().size());
    }

    @Test
    void getById() {
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> accountService.getById(1L));

        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(new Account()));
        when(accountMapper.toDto(any())).thenReturn(new AccountDto());
        assertNotNull(accountService.getById(1L));
    }

    @Test
    void create() {
        Account account = new Account();
        when(accountMapper.toEntity(any())).thenReturn(account);
        when(accountRepository.save(any())).thenReturn(account);
        when(accountMapper.toDto(account)).thenReturn(new AccountDto());
        assertNotNull(accountService.create(any()));
    }

    @Test
    void getAllByFilter() {
        when(accountSpecification.getAccounts(any()))
            .thenReturn((Specification<Account>) (root, query, criteriaBuilder) -> null);
        when(accountRepository.findAll((Specification<Account>) any())).thenReturn(List.of(new Account()));
        when(accountMapper.toDtoList(anyList())).thenReturn(List.of(new AccountDto()));
        assertEquals(1, accountService.getAllByFilter(new AccountFilterDto()).size());
    }
}