package ru.clevertec.checkrunner.util.json.data;

import java.util.Objects;

public class Address {

    private House house;

    private Boolean isApartment;

    private Long houseNumber;

    public Address() {
        house = new House();
        isApartment = true;
        houseNumber = 40L;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public Boolean getApartment() {
        return isApartment;
    }

    public void setApartment(Boolean apartment) {
        isApartment = apartment;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
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
