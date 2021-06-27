package org.openblock.creator.impl.java.clazz.writer;

import org.openblock.creator.code.clazz.writer.AbstractClassWriter;
import org.openblock.creator.code.clazz.writer.ClassWriter;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.stream.Collectors;

public class JavaClassWriter extends AbstractClassWriter<JavaClass> implements ClassWriter<JavaClass> {
    @Override
    public String write(JavaClass clazz) {
        String imports = this.writeImports(clazz);
        if (imports.length() != 0) {
            imports = imports + "\n\n";
        }
        return this.writePackage(clazz) +
                "\n\n" +
                imports +
                this.writeClassInit(clazz) +
                "\n" +
                clazz.getFunctions().stream().sorted().map(f -> f.writeCode(1)).collect(Collectors.joining("\n\n")) +
                "\n}";
    }


}
