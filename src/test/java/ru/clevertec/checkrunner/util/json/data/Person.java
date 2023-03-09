package ru.clevertec.checkrunner.util.json.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Person {

    private static String type;

    private  String name;

    private int age;

    private Address address;

    private Character gender;

    public Person() {
    }

    public Person(String name, int age, Address address, Character gender) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.gender = gender;
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Person.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(address, person.address) && Objects.equals(gender, person.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, address, gender);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", gender=" + gender +
                '}';
    }
}
