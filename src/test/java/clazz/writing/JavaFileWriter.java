package clazz.writing;

import org.openblock.creator.impl.java.clazz.JavaClass;

import java.io.File;

public class JavaFileWriter {

    public void testIsWrittenCorrectly() {
        Class<?> clazz = File.class;

        JavaClass javaClass = new JavaClass(clazz);
        String output = javaClass.writeCode();

        System.out.println("Class:");

        System.out.println(output);
    }

    public static void main(String[] args) {
        new JavaFileWriter().testIsWrittenCorrectly();
    }
}
