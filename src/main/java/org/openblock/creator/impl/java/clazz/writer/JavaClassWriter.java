package org.openblock.creator.impl.java.clazz.writer;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.writer.AbstractClassWriter;
import org.openblock.creator.code.clazz.writer.ClassWriter;
import org.openblock.creator.code.variable.field.Field;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.List;
import java.util.stream.Collectors;

public class JavaClassWriter extends AbstractClassWriter<JavaClass> implements ClassWriter<JavaClass> {
    @Override
    public @NotNull String write(JavaClass clazz) {
        String imports = this.writeImports(clazz);
        if (!imports.isEmpty()) {
            imports = imports + "\n\n";
        }
        List<Field> fields = clazz.getFields();
        String fieldStr = "";
        if (!fields.isEmpty()) {
            fieldStr = "\n\t" + fields.stream().map(this::writeField).collect(Collectors.joining("\n\t")) + "\n\n";
        }
        return this.writePackage(clazz) +
                "\n\n" +
                imports +
                this.writeClassInit(clazz) +
                "\n" +
                fieldStr +
                clazz.getFunctions().stream().sorted().map(f -> f.writeCode(1)).collect(Collectors.joining("\n\n")) +
                "\n}";
    }


}
