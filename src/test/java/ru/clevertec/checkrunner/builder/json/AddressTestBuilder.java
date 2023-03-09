package ru.clevertec.checkrunner.builder.json;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.util.json.data.Address;
import ru.clevertec.checkrunner.util.json.data.House;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aAddress")
public class AddressTestBuilder implements TestBuilder<Address> {

    private House house = HouseTestBuilder.aHouse().build();

    private Boolean isApartment = true;

    private Long houseNumber = 40L;

    @Override
    public Address build() {
        final Address address = new Address();
        address.setHouse(house);
        address.setIsApartment(isApartment);
        address.setHouseNumber(houseNumber);
        return address;
    }
}
