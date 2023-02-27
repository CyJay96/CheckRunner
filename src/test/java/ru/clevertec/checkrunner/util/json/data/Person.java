package ru.clevertec.checkrunner.util.json.data;

import java.util.Objects;

public class Person {

    private static String type;

    private final String name;

    private int age;

    private Address address;

    private Character gender;

    public Person() {
        type = "Person";
        name = "Tom";
        age = 21;
        address = new Address();
        gender = 'M';
    }

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        Person.type = type;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
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
