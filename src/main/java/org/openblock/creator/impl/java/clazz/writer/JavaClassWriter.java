package org.openblock.creator.impl.java.clazz.writer;

import org.openblock.creator.code.clazz.writer.AbstractClassWriter;
import org.openblock.creator.code.clazz.writer.ClassWriter;
import org.openblock.creator.impl.java.clazz.JavaClass;

public class JavaClassWriter extends AbstractClassWriter<JavaClass> implements ClassWriter<JavaClass> {
    @Override
    public String write(JavaClass clazz) {
        return this.writePackage(clazz) +
                "\n" +
                this.writeImports(clazz) +
                "\n" +
                this.writeClassInit(clazz) +
                "\n" +
                clazz.getFunctions().
        "}";
    }


}
