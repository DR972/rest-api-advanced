package com.epam.esm.service.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.SortTypeException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The class {@code SortTypeValidator} provides method to Sorting Type validate.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@Component
public class SortTypeValidator {
    private static final String TAGS = "tags";
    private static final String ORDERS = "customerOrders";
    private static final String ID = "id";

    /**
     * This method validate type of sort.
     *
     * @param types List<String> types
     */
    public void validateSortType(List<String> types) {
        List<String> fields = Arrays.stream(GiftCertificate.class.getDeclaredFields()).map(Field::getName)
                .filter(f -> !f.equals(TAGS) && !f.equals(ORDERS)).collect(Collectors.toList());
        List<String> badTypes = types.stream()
                .map(t -> {
                    if (t.startsWith("-")) return t.substring(1);
                    else return t;
                })
                .filter(t -> !fields.contains(t) && !t.equals(ID))
                .collect(Collectors.toList());
        if (!badTypes.isEmpty()) {
            throw new SortTypeException("ex.sortType", badTypes.stream().collect(Collectors.joining(",", "(", ")")));
        }
    }
}
