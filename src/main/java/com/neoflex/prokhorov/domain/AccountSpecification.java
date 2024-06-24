package com.neoflex.prokhorov.domain;

import com.neoflex.prokhorov.service.dto.AccountFilterDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.neoflex.prokhorov.values.AccountFilterField.*;

@Component
public class AccountSpecification {

    public Specification<Account> getAccounts(AccountFilterDto filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            addStringPredicate(criteriaBuilder, predicates, root.get(SURNAME.getValue()), filter.getSurname());
            addStringPredicate(criteriaBuilder, predicates, root.get(NAME.getValue()), filter.getName());
            addStringPredicate(criteriaBuilder, predicates, root.get(PATRONYMIC.getValue()), filter.getPatronymic());
            addStringPredicate(criteriaBuilder, predicates, root.get(PHONE.getValue()), filter.getPhone());
            addStringPredicate(criteriaBuilder, predicates, root.get(MAIL.getValue()), filter.getMail());

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void addStringPredicate(
        CriteriaBuilder criteriaBuilder,
        List<Predicate> predicates,
        Path targetPath,
        String source
    ) {
        if (Strings.isNotEmpty(source)) {
            predicates.add(criteriaBuilder.like(targetPath, getPattern(source)));
        }
    }

    private String getPattern(String field) {
        return new StringBuilder("%").append(field.trim()).append("%").toString();
    }
}
