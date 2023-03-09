package ru.clevertec.checkrunner.builder.json;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.util.json.data.Floor;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aFloor")
public class FloorTestBuilder implements TestBuilder<Floor> {

    private Byte width = 8;

    private Float height = 6.4f;

    @Override
    public Floor build() {
        final Floor floor = new Floor();
        floor.setWidth(width);
        floor.setHeight(height);
        return floor;
    }
}
