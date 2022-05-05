package org.wyj.blog.supertest;

public class PersonA extends Person{

    private String name;

    public PersonA(){}

    public PersonA(String name){this.name = name;}

    public void eat(){
        System.out.printf("PersonA %s eat", this.name);
    }
}
