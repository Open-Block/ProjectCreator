package clazz.writing;

import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaFileWriter {

    public void testIsWrittenCorrectly(Class<?> clazz) {
        JavaClass javaClass = new JavaClass(clazz);
        String output = javaClass.writeCode();

        System.out.println("Class:");

        System.out.println(output);
    }

    public static void main(String[] args) {
        JavaFileWriter writer = new JavaFileWriter();
        List<Integer> clazz = new ArrayList<>();
        clazz.add(1);
        //writer.testIsWrittenCorrectly(File.class);
        //writer.testIsWrittenCorrectly(Path.class);
        writer.testIsWrittenCorrectly(clazz.getClass());
    }
}
