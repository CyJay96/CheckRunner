package ru.clevertec.checkrunner.util.json.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Floor {

    private Byte width;

    private Float height;

    public Floor() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return Objects.equals(width, floor.width) && Objects.equals(height, floor.height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height);
    }

    @Override
    public String toString() {
        return "Floor{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
