package org.openblock.creator.impl.custom.clazz;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.writer.AbstractClassWriter;

import java.util.stream.Collectors;

public class CustomClassWriter<T extends AbstractCustomClass> extends AbstractClassWriter<T> {

    @Override
    public @NotNull String write(T clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.writePackage(clazz));
        builder.append("\n");
        builder.append(this.writeImports(clazz));
        builder.append("\n");
        builder.append(this.writeClassInit(clazz));
        builder.append("\n");
        builder.append(clazz.getFields().stream().map(f -> f.writeCode(1)).collect(Collectors.joining("\n")));
        builder.append("\n");
        builder.append(clazz.getFunctions().stream().map(f -> f.writeCode(1, clazz.getClassType())).collect(Collectors.joining("\n")));
        builder.append("}");

        return builder.toString();
    }
}
