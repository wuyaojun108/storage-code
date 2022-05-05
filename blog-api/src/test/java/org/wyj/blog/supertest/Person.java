package org.wyj.blog.supertest;

public class Person {
    private String name;

    public Person(){}

    public Person(String name){this.name = name;}

    public void eat(){
        System.out.printf("Person %s eat\n", this.name);
    }

    public static class Inner{
        private String iName;

        public Inner(){}

        public Inner(String iName){this.iName = iName;}

        public void eat(){
            System.out.printf("Person %s eat\n", this.iName);
        }

    }
}
