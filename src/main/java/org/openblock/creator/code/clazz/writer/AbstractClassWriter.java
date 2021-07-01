package org.openblock.creator.code.clazz.writer;

import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.function.IConstructor;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.code.variable.field.Field;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public abstract class AbstractClassWriter<C extends IClass> implements ClassWriter<C> {

    protected String writePackage(C cClass) {
        return "package " + String.join(".", cClass.getPackage()) + ";";
    }

    protected String writeImports(C cClass) {
        TreeSet<String> imports = cClass
                .getImports()
                .parallelStream()
                .filter(c -> c.getPackage().length != 0)
                .filter(c -> {
                    String[] pack = c.getPackage();
                    if (pack.length != 2) {
                        return true;
                    }
                    if (!pack[0].equals("java")) {
                        return true;
                    }
                    return !pack[1].equals("lang");
                })
                .map(c -> "import " + String.join(".", c.getPackage()) + "." + c.getName() + ";")
                .collect(Collectors.toCollection(TreeSet::new));
        return String.join("\n", imports);
    }

    public String writeField(Field field) {
        String fin = "";
        String sta = "";
        if (field.isFinal()) {
            fin = "final ";
        }
        if (field.isStatic()) {
            sta = "static ";
        }
        return field.getVisibility().getDisplayName() + " " + sta + fin + field.getReturnType().getDisplayText() + " " + field.getName();
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
        builder.append(cClass.getVisibility().getDisplayName()).append(" ");
        if (cClass.isFinal()) {
            builder.append("final ");
        }
        builder.append("interface ");
        builder.append(cClass.getName());
        builder.append(" ");
        cClass.getGenerics().forEach(generic -> builder.append(generic.writeCode(0)).append(" "));
        if (!cClass.getImplements().isEmpty()) {
            builder.append("extends ").append(cClass.getImplements().stream().map(SpecifiedGenerics::getDisplayName).collect(Collectors.joining(", ")));
        }
        builder.append(" {");
        return builder.toString();
    }

    private String writeStandardClassInit(C cClass) {
        StringBuilder builder = new StringBuilder();
        builder.append(cClass.getVisibility().getDisplayName()).append(" ");
        if (cClass.isAbstract()) {
            builder.append("abstract ");
        }
        if (cClass.isFinal()) {
            builder.append("final ");
        }
        builder.append("class ");
        builder.append(cClass.getName());
        builder.append(" ");
        List<IGeneric> generics = cClass.getGenerics();
        if (!generics.isEmpty()) {
            builder.append("<");
            String genericsStr = generics.stream().map(generic -> generic.writeCode(true)).collect(Collectors.joining(", "));
            builder.append(genericsStr);
            builder.append("> ");
        }
        cClass.getExtendingClass().ifPresent(extending -> builder.append("extends ").append(extending.getDisplayName()).append(" "));
        if (!cClass.getImplements().isEmpty()) {
            builder.append("implements ").append(cClass.getImplements().stream().map(SpecifiedGenerics::getDisplayName).collect(Collectors.joining(", ")));
        }
        builder.append(" {");
        return builder.toString();
    }

}
