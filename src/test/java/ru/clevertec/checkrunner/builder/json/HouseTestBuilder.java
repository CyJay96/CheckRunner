package ru.clevertec.checkrunner.builder.json;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.util.json.data.House;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aHouse")
public class HouseTestBuilder implements TestBuilder<House> {

    private int floorCount = 5;

    @Override
    public House build() {
        final House house = new House();
        house.setFloorCount(floorCount);
        return house;
    }
}
