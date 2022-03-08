package com.ozmaden;

import com.ozmaden.anyOf.AnyOf;
import com.ozmaden.constrained.Constrained;
import com.ozmaden.notBlank.NotBlank;
import com.ozmaden.notEmpty.NotEmpty;
import com.ozmaden.notNull.NotNull;
import com.ozmaden.size.Size;

import java.util.List;

/*
Чуть чуть изменённый образец из примера для более хорошего тестирования
 */
@Constrained
public class BookingForm {
    @NotNull
    @Size(min = 1, max = 3)
    private List<@NotNull GuestForm> guests;

    @NotNull
    @NotEmpty
    private List<@AnyOf({"TV", "Kitchen", "PC"}) String> amenities;

    @NotNull
    @NotBlank
    @AnyOf({"House", "Hostel", "Apartment"})
    private String propertyType;

    @NotNull
    private Unrelated unrelated;

    public BookingForm(List<GuestForm> guests, List<String> amenities, String
            propertyType, Unrelated unrelated) {
        this.guests = guests;
        this.amenities = amenities;
        this.propertyType = propertyType;
        this.unrelated = unrelated;
    }
}
