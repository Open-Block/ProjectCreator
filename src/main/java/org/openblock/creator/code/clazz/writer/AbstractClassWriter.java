package org.openblock.creator.code.clazz.writer;

import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.SpecifiedGenerics;
import org.openblock.creator.code.function.IMethod;

import java.util.stream.Collectors;

public abstract class AbstractClassWriter<C extends IClass> implements ClassWriter<C> {

    protected String writePackage(C cClass) {
        return "package " + String.join(".", cClass.getPackage()) + ";";
    }

    protected String writeImports(C cClass) {
        return cClass.getImports().stream().map(c -> "import " + String.join(".", c.getPackage()) + ";").collect(Collectors.joining("\n"));
    }


    //TODO - put this in standard method
    private String writeStandardMethodInit(IMethod function) {
        StringBuilder builder = new StringBuilder();
        builder.append(function.getVisibility().name().toLowerCase());
        builder.append(" ");
        if (function.isStatic()) {
            builder.append("static ");
        }
        if (!function.getGenerics().isEmpty()) {
            String generics = function.getGenerics().stream().map(Codeable::writeCode).collect(Collectors.joining(", "));
            builder.append("<");
            builder.append(generics);
            builder.append("> ");
        }
        builder.append(function.getReturnType().getDisplayText());
        builder.append(" ");
        builder.append(function.getName());
        builder.append(" (");
        builder.append(function
                .getParameters()
                .stream()
                .map(p -> (p.isFinal() ? "final " : "")
                        + p.getReturnType().getDisplayText()
                        + " " + p.getName()
                )
                .collect(Collectors.joining(", ")));
        builder.append(") {\n");
        function.getCodeBlock().forEach(c -> builder.append("\n").append(c.writeCode()));
        builder.append("}");
        return builder.toString();
    }

    protected String writeClassInit(C cClass) {
        return switch (cClass.getClassType()) {
            case STANDARD -> writeStandardClassInit(cClass);
            case INTERFACE -> writeInterfaceClassInit(cClass);
            default -> throw new RuntimeException("Unknown ClassType: " + cClass.getClassType().name());
        };
    }

    private String writeInterfaceClassInit(C cClass) {
        StringBuilder builder = new StringBuilder();
        builder.append(cClass.getVisibility().name().toLowerCase()).append(" ");
        if (cClass.isAbstract()) {
            builder.append("abstract ");
        }
        if (cClass.isFinal()) {
            builder.append("final ");
        }
        builder.append("class ");
        builder.append(cClass.getName());
        builder.append(" ");
        cClass.getGenerics().forEach(generic -> builder.append(generic.writeCode()).append(" "));
        if (!cClass.getImplements().isEmpty()) {
            builder.append("extends ").append(cClass.getImplements().stream().map(SpecifiedGenerics::getDisplayName).collect(Collectors.joining(", ")));
        }
        builder.append(" {");
        return builder.toString();
    }

    private String writeStandardClassInit(C cClass) {
        StringBuilder builder = new StringBuilder();
        builder.append(cClass.getVisibility().name().toLowerCase()).append(" ");
        if (cClass.isAbstract()) {
            builder.append("abstract ");
        }
        if (cClass.isFinal()) {
            builder.append("final ");
        }
        builder.append("class ");
        builder.append(cClass.getName());
        builder.append(" ");
        cClass.getGenerics().forEach(generic -> builder.append(generic.writeCode()).append(" "));
        cClass.getExtendingClass().ifPresent(extending -> builder.append("extends ").append(extending.getDisplayName()).append(" "));
        if (!cClass.getImplements().isEmpty()) {
            builder.append("implements ").append(cClass.getImplements().stream().map(SpecifiedGenerics::getDisplayName).collect(Collectors.joining(", ")));
        }
        builder.append(" {");
        return builder.toString();
    }

}
