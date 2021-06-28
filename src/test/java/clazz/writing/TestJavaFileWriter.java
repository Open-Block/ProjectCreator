package clazz.writing;

import org.openblock.creator.impl.java.clazz.JavaClass;

import java.io.File;

public class TestJavaFileWriter {

    public void testIsWrittenCorrectly(Class<?> clazz) {
        JavaClass javaClass = new JavaClass(clazz);
        String output = javaClass.writeCode();

        System.out.println("Class:");

        System.out.println(output);
    }

    public static void main(String[] args) {
        TestJavaFileWriter writer = new TestJavaFileWriter();
        //writer.testIsWrittenCorrectly(File.class);
        //writer.testIsWrittenCorrectly(Path.class);
        writer.testIsWrittenCorrectly(File.class);
    }
}
