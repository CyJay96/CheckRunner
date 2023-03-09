package ru.clevertec.checkrunner.builder.json;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.checkrunner.builder.TestBuilder;
import ru.clevertec.checkrunner.util.json.data.Address;
import ru.clevertec.checkrunner.util.json.data.Person;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aPerson")
public class PersonTestBuilder implements TestBuilder<Person> {

    private static String type = "Person";

    private String name = "Tom";

    private int age = 21;

    private Address address = AddressTestBuilder.aAddress().build();

    private Character gender = 'M';

    @Override
    public Person build() {
        final Person person = new Person();
        Person.setType(type);
        person.setName(name);
        person.setAge(age);
        person.setAddress(address);
        person.setGender(gender);
        return person;
    }
}
