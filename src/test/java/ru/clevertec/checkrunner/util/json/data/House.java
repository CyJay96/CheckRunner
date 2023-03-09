package ru.clevertec.checkrunner.util.json.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class House {

    private int floorCount;

    public House() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return floorCount == house.floorCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(floorCount);
    }

    @Override
    public String toString() {
        return "House{" +
                "floorCount=" + floorCount +
                '}';
    }
}
