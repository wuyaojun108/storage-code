package org.wyj;

import org.junit.Test;

import java.io.File;

public class FileTest {
    @Test
    public void fileTest(){
        File file = new File("aa.jsp");
        System.out.println("file.exists() = " + file.exists());

    }
}
