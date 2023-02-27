package ru.clevertec.checkrunner.util.json.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class House {

    private List<Floor> floors;

    private double[] receipts;

    public House() {
        floors = new ArrayList<>();
        floors.add(new Floor());
        floors.add(new Floor());
        receipts = new double[] {1.2, 4.6};
    }

    public List<Floor> getFloors() {
        return floors;
    }

    public void setFloors(List<Floor> floors) {
        this.floors = floors;
    }

    public double[] getReceipts() {
        return receipts;
    }

    public void setReceipts(double[] receipts) {
        this.receipts = receipts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Objects.equals(floors, house.floors) && Arrays.equals(receipts, house.receipts);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(floors);
        result = 31 * result + Arrays.hashCode(receipts);
        return result;
    }

    @Override
    public String toString() {
        return "House{" +
                "floors=" + floors +
                ", receipts=" + Arrays.toString(receipts) +
                '}';
    }
}
