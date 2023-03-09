package ru.clevertec.checkrunner.util.json.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Address {

    private House house;

    private Boolean isApartment;

    private Long houseNumber;

    public Address() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(house, address.house) && Objects.equals(isApartment, address.isApartment) && Objects.equals(houseNumber, address.houseNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(house, isApartment, houseNumber);
    }

    @Override
    public String toString() {
        return "Address{" +
                "house=" + house +
                ", isApartment=" + isApartment +
                ", houseNumber=" + houseNumber +
                '}';
    }
}
