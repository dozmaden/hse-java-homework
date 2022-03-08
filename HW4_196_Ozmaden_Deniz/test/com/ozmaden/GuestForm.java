package com.ozmaden;

import com.ozmaden.anyOf.AnyOf;
import com.ozmaden.constrained.Constrained;
import com.ozmaden.inRange.InRange;
import com.ozmaden.negative.Negative;
import com.ozmaden.notBlank.NotBlank;
import com.ozmaden.notNull.NotNull;
import com.ozmaden.positive.Positive;

/*
Чуть чуть изменённый образец из примера для более хорошего тестирования
 */
@Constrained
public class GuestForm {
    @NotNull
    @NotBlank
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;

    @NotNull
    @Positive
    @InRange(min = 0, max = 110)
    private int age;

    @NotNull
    @Negative
    private int debt;

    public GuestForm(String firstName, String lastName, int age, int debt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.debt = debt;
    }
}
