package com.neoflex.prokhorov.aop;

import com.neoflex.prokhorov.service.dto.AccountDto;
import com.neoflex.prokhorov.values.ApplicationType;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountValidationTest {
    @InjectMocks
    private AccountValidation accountValidation;
    @Mock
    private ProceedingJoinPoint joinPoint;

    @Test
    @SneakyThrows
    void checkValidCommonCase() {
        AccountDto account = new AccountDto();
        assertThrow("mob", account);
        assertThrow(ApplicationType.MOBILE.getHeader(), account);
        assertThrow(ApplicationType.BANK.getHeader(), account);
        assertThrow(ApplicationType.MAIL.getHeader(), account);
        assertThrow(ApplicationType.GOSUSLUGI.getHeader(), account);
    }

    @Test
    @SneakyThrows
    void checkValidMobileCases() {
        AccountDto account = new AccountDto();
        String sourceHeader = ApplicationType.MOBILE.getHeader();
        assertThrow(sourceHeader, account);

        account.setPhone("89000000000");
        assertThrow(sourceHeader, account);

        account.setPhone("79000000000");
        when(joinPoint.proceed()).thenReturn(account);
        assertEquals(account, accountValidation.checkValid(joinPoint, sourceHeader, account));
    }

    @Test
    @SneakyThrows
    void checkValidMailCases() {
        AccountDto account = new AccountDto();
        String sourceHeader = ApplicationType.MAIL.getHeader();
        account.setPhone("79000000000");
        when(joinPoint.proceed()).thenReturn(account);
        assertThrow(sourceHeader, account);

        account.setName("Jack");
        assertThrow(sourceHeader, account);

        account.setMail("Jack@");
        assertThrow(sourceHeader, account);

        account.setMail("testmail@box.com");
        assertEquals(account, accountValidation.checkValid(joinPoint, sourceHeader, account));
    }

    @Test
    @SneakyThrows
    void checkValidBankCases() {
        AccountDto account = new AccountDto();
        String sourceHeader = ApplicationType.BANK.getHeader();
        account.setPhone("79000000000");
        account.setName("Jack");
        account.setMail("testmail@box.com");
        when(joinPoint.proceed()).thenReturn(account);
        assertThrow(sourceHeader, account);

        account.setBankId("2439s-asfverg2v");
        assertThrow(sourceHeader, account);

        account.setSurname("Doe");
        assertThrow(sourceHeader, account);

        account.setPatronymic("Alan");
        assertThrow(sourceHeader, account);

        account.setBirth(LocalDate.now());
        assertThrow(sourceHeader, account);

        account.setPassport("00001122");
        assertThrow(sourceHeader, account);

        account.setPassport("0000112233");
        assertEquals(account, accountValidation.checkValid(joinPoint, sourceHeader, account));
    }

    @Test
    @SneakyThrows
    void checkValidGosuslugiCases() {
        AccountDto account = new AccountDto();
        String sourceHeader = ApplicationType.GOSUSLUGI.getHeader();
        account.setPhone("79000000000");
        account.setName("Vladimir");
        account.setMail("testmail@box.com");
        account.setBankId("2439s-asfverg2v");
        account.setSurname("Ulyaniov");
        account.setPatronymic("Ilyich");
        account.setPassport("0000112233");
        account.setBirth(LocalDate.now());
        when(joinPoint.proceed()).thenReturn(account);
        assertThrow(sourceHeader, account);

        account.setRegistrationAddress("Moscow, Mavzoley Lenina");
        assertThrow(sourceHeader, account);

        account.setBirthPlace("Simbirsk city");
        assertEquals(account, accountValidation.checkValid(joinPoint, sourceHeader, account));
    }

    private void assertThrow(String sourceHeader, AccountDto account) {
        assertThrows(
                ConstraintViolationException.class,
                () -> accountValidation.checkValid(joinPoint, sourceHeader, account)
        );
    }
}