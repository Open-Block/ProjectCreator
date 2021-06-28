package org.openblock.creator.impl.custom.clazz.interfacetype;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.writer.AbstractClassWriter;

import java.util.stream.Collectors;

public class CustomInterfaceWriter extends AbstractClassWriter<CustomInterface> {
    @Override
    public @NotNull String write(CustomInterface clazz) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.writePackage(clazz));
        builder.append("\n");
        builder.append(this.writeImports(clazz));
        builder.append("\n");
        builder.append(this.writeClassInit(clazz));
        builder.append("\n");
        builder.append(clazz.getFunctions().stream().map(f -> f.writeCode(1, ClassType.INTERFACE)).collect(Collectors.joining("\n")));
        builder.append("}");

        return builder.toString();
    }
}
