package com.neoflex.prokhorov.web;

import com.neoflex.prokhorov.aop.AccountConstraintAnnotation;
import com.neoflex.prokhorov.service.AccountService;
import com.neoflex.prokhorov.service.dto.AccountDto;
import com.neoflex.prokhorov.service.dto.AccountFilterDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {
    AccountService accountService;

    @PostMapping("all")
    List<AccountDto> getAllByFilter(@RequestBody(required = false) AccountFilterDto dto) {
        if (Objects.isNull(dto)) {
            return accountService.getAll();
        }
        return accountService.getAllByFilter(dto);
    }

    @GetMapping("{id}")
    AccountDto getById(@PathVariable Long id) {
        return accountService.getById(id);
    }

    @PostMapping
    @AccountConstraintAnnotation
    AccountDto create(
        @RequestHeader(name = "x-Source", required = false) String applicationType, @RequestBody AccountDto dto
    ) {
        return accountService.create(dto);
    }

    @GetMapping("by-username")
    AccountDto getByUsername(@RequestParam String username) {
        return accountService.findByLogin(username);
    }
}
