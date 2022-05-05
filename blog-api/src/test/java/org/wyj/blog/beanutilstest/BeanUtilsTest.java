package org.wyj.blog.beanutilstest;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.wyj.blog.beanutilstest.beans.Person;
import org.wyj.blog.beanutilstest.beans.Student;

public class BeanUtilsTest {

    // 测试BeanUtils的功能
    @Test
    public void beanUtilsTest(){
        Person person = new Person("zs", "aa");
        Student student = new Student();
        BeanUtils.copyProperties(person, student);
        System.out.println("student = " + student);
    }

}
