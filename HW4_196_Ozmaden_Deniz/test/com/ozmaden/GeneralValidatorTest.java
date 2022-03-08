package com.ozmaden;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GeneralValidatorTest {

    /*
    Тут тестируем пути,  в правильных ли местах наш Валидатор находит ошибки
     */
    @Test
    void validate() {
        List<GuestForm> guests = List.of(
                new GuestForm("Alex", "Jones",47,  100000),// ошибка в debt
                new GuestForm(null, "", -69, -1) // ошибка null, "", -69
        );
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                List.of("PC", "Piano"), // ошибка в Piano
                "Loft", // ошибка
                unrelated
        );

        GeneralValidator valid = new GeneralValidator();
        Set<ValidationError> validationErrors = valid.validate(bookingForm);

        var paths = validationErrors.parallelStream().map(ValidationError::getPath).
                collect(Collectors.toList());

        assertTrue(paths.contains("guests[0].debt"));
        assertTrue(paths.contains("guests[1].firstName"));
        assertTrue(paths.contains("guests[1].lastName"));
        assertTrue(paths.contains("guests[1].age"));
        assertTrue(paths.contains("amenities[1]"));
        assertTrue(paths.contains("propertyType"));
    }

    @Test
    void validate2() {
        List<GuestForm> guests = List.of(
                new GuestForm("Alex", "Jones",47,  100000),
                new GuestForm("John", "Doe", -69, -1), // ошибки
                new GuestForm("Gary", "James",0,  456789), // ошибка
                new GuestForm("Donald", "Obama",120,  987)
        ); // ошибка, слишком длинный список
        Unrelated unrelated = new Unrelated(-1);
        BookingForm bookingForm = new BookingForm(
                guests,
                List.of(), // ошибка
                null, // ошибка
                unrelated
        );

        GeneralValidator valid = new GeneralValidator();
        Set<ValidationError> validationErrors = valid.validate(bookingForm);

        var failedvls = validationErrors.parallelStream().map(ValidationError::getFailedValue).
                collect(Collectors.toList());

        var failedmsgs = validationErrors.parallelStream().map(ValidationError::getMessage).
                collect(Collectors.toList());

        assertTrue(failedvls.contains(120));
        assertTrue(failedvls.contains(987));
        assertTrue(failedvls.contains(-69));
        assertTrue(failedvls.contains(456789));
        assertTrue(failedvls.contains(Collections.emptyList()));
        assertTrue(failedvls.contains(null));
        assertTrue(failedmsgs.contains("Object is of incorrect size!"));
    }
}
