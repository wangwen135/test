package com.wwh.test.streamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectTest {

    public static void main(String[] args) {
        List<Person> list = getPersonList();
        List<Integer> ageList = list.stream()
            .map(Person::getAge)
            .distinct()
            .filter(x -> x != null)
            .collect(Collectors.toList());
        System.out.println(ageList);

        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(Person::getId, x -> x));
        System.out.println(map);
    }

    public static List<Person> getPersonList() {
        List<Person> list = new ArrayList<Person>();
        list.add(new Person(1, "zhangs", 11));
        list.add(new Person(2, "lisi", 12));
        list.add(new Person(3, "wangw", 13));
        list.add(new Person(4, "zhangl", 14));
        list.add(new Person(5, "lisi", 11));
        list.add(new Person(6, "xxsi", null));
        return list;
    }
}

class Person {

    private Integer id;
    private String name;
    private Integer age;

    public Person(Integer id, String name, Integer age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(){
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
    }

}
