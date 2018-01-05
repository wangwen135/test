package com.wwh.yaml;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private String name;
    private int age;
    private String Sex;
    private List<Person> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

    public synchronized void addChidern(Person child) {
        if (children == null) {
            children = new ArrayList<>();
        }

        children.add(child);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Person [");
        if (name != null) {
            builder.append("name=");
            builder.append(name);
            builder.append(", ");
        }
        builder.append("age=");
        builder.append(age);
        builder.append(", ");
        if (Sex != null) {
            builder.append("Sex=");
            builder.append(Sex);
            builder.append(", ");
        }
        if (children != null) {
            builder.append("children=");
            builder.append(children);
        }
        builder.append("]");
        return builder.toString();
    }
    
    
}
