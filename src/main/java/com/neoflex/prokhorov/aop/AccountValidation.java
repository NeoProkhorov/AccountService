package com.neoflex.prokhorov.aop;

import com.neoflex.prokhorov.service.dto.AccountDto;
import com.neoflex.prokhorov.values.ApplicationType;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.BiPredicate;

@Component
@Aspect
@Slf4j
public class AccountValidation {
    private final String APPLICATION_MSG = "Попытка сохранения аккаунта из неизвестного ресурса: %s";
    private final String VALID_MSG = "Ошибка валидации: обязательные поля ресурса %s не заполнены";
    private final String PASSPORT_MSG = "Ошибка валидации: поле пасспорт должно содержать 10 цифр";
    private final String PHONE_MSG = "Ошибка валидации: номер телефона содержать 11 цифр, начиная с 7";
    private final String MAIL_MSG = "Некорректный адрес электронной почты";
    private final String PASSPORT_REGEX = "\\d{10}";
    private final String MAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private final String PHONE_REGEX = "^(7)\\d{10}";

    private final BiPredicate<String, String> MATCH_PREDICATE = (s, regex) -> Strings.isEmpty(s) || s.matches(regex);

    @Around("@annotation(com.neoflex.prokhorov.aop.AccountConstraintAnnotation) && args(type, dto)")
    public AccountDto checkValid(ProceedingJoinPoint joinPoint, String type, AccountDto dto) throws Throwable {
        log.info("Валидация при сохранении аккаунта");

        ApplicationType applicationType = ApplicationType.getByHeader(type).orElseThrow(
            () -> new ConstraintViolationException(String.format(APPLICATION_MSG, type), null)
        );

        if (!isApplicationValid(applicationType, dto)) {
            throw new ConstraintViolationException(String.format(VALID_MSG, applicationType.getHeader()), null);
        }

        if (!MATCH_PREDICATE.test(dto.getPassport(), PASSPORT_REGEX)) {
            throw new ConstraintViolationException(PASSPORT_MSG, null);
        }

        if (!MATCH_PREDICATE.test(dto.getPhone(), PHONE_REGEX)) {
            throw new ConstraintViolationException(PHONE_MSG, null);
        }

        if (!MATCH_PREDICATE.test(dto.getMail(), MAIL_REGEX)) {
            throw new ConstraintViolationException(MAIL_MSG, null);
        }

        return (AccountDto) joinPoint.proceed();
    }

    private boolean isApplicationValid(ApplicationType applicationType, AccountDto dto) {
        return switch (applicationType) {
            case BANK -> Objects.nonNull(dto.getBankId()) && Objects.nonNull(dto.getSurname()) &&
                Objects.nonNull(dto.getName()) && Objects.nonNull(dto.getPatronymic()) &&
                Objects.nonNull(dto.getBirth()) && Objects.nonNull(dto.getPassport());
            case MAIL -> Objects.nonNull(dto.getMail()) && Objects.nonNull(dto.getName());
            case MOBILE -> Objects.nonNull(dto.getPhone());
            case GOSUSLUGI -> Objects.nonNull(dto.getBankId()) && Objects.nonNull(dto.getSurname()) &&
                Objects.nonNull(dto.getName()) && Objects.nonNull(dto.getPatronymic()) &&
                Objects.nonNull(dto.getBirth()) && Objects.nonNull(dto.getPassport()) &&
                Objects.nonNull(dto.getPhone()) && Objects.nonNull(dto.getRegistrationAddress()) &&
                Objects.nonNull(dto.getBirthPlace());
        };
    }
}
