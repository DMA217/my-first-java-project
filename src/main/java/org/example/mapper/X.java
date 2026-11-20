package org.example.mapper;

import java.time.LocalDateTime;

public class X {
    private String name;
    private Integer age;
    private Double weight;
    private LocalDateTime birthDate;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public LocalDateTime getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDateTime birthDate) { this.birthDate = birthDate; }

    @Override
    public String toString() {
        return "X{name='" + name + "', age=" + age + ", weight=" + weight + ", birthDate=" + birthDate + "}";
    }
}