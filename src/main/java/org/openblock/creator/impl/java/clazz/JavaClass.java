package org.openblock.creator.impl.java.clazz;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.call.returntype.StatedReturnType;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.specified.NoGenerics;
import org.openblock.creator.code.clazz.generic.specified.SpecifiedGenerics;
import org.openblock.creator.code.clazz.type.SpecifiedGenericType;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.code.variable.field.Field;
import org.openblock.creator.code.variable.field.UninitiatedField;
import org.openblock.creator.impl.java.clazz.generic.JavaGenerics;
import org.openblock.creator.impl.java.clazz.generic.specified.JavaClassGenerics;
import org.openblock.creator.impl.java.clazz.writer.JavaClassWriter;
import org.openblock.creator.impl.java.function.constructor.JavaConstructor;
import org.openblock.creator.impl.java.function.method.JavaMethod;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TODO functions


public class JavaClass implements IClass {

    private final Class<?> clazz;

    public JavaClass(Class<?> clazz) {
        if (clazz.isArray()) {
            clazz = clazz.componentType();
        }
        this.clazz = clazz;
    }

    public Class<?> getTargetClass() {
        return this.clazz;
    }

    @Override
    public @NotNull String getName() {
        return this.clazz.getSimpleName();
    }

    @Override
    public String[] getPackage() {
        Package packageValue = this.clazz.getPackage();
        if (packageValue == null) {
            return new String[0];
        }
        return packageValue.getName().split(Pattern.quote("."));
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(this.clazz.getModifiers());
    }

    @Override
    public @NotNull ClassType getClassType() {
        if (this.clazz.isEnum()) {
            return ClassType.ENUM;
        }
        if (this.clazz.isInterface()) {
            return ClassType.INTERFACE;
        }
        if (this.clazz.isAnnotation()) {
            return ClassType.ANNOTATION;
        }
        return ClassType.STANDARD;
    }

    @Override
    public @NotNull Visibility getVisibility() {
        int modifier = this.clazz.getModifiers();
        if (Modifier.isPublic(modifier)) {
            return Visibility.PUBLIC;
        }
        if (Modifier.isPrivate(modifier)) {
            return Visibility.PRIVATE;
        }
        if (Modifier.isProtected(modifier)) {
            return Visibility.PROTECTED;
        }
        return Visibility.PACKAGED;
    }

    @Override
    public List<IGeneric> getGenerics() {
        Type[] types = this.clazz.getGenericInterfaces();
        if (types.length == 0) {
            return Collections.emptyList();
        }
        return calcGenerics(this.clazz).stream().flatMap(s -> s.getGenerics().stream()).collect(Collectors.toList());
    }

    @Override
    public List<Field> getFields() {
        return Stream.of(this.clazz.getFields()).map(f -> {
            Visibility visibility = Visibility.PACKAGED;
            if (Modifier.isPrivate(f.getModifiers())) {
                visibility = Visibility.PRIVATE;
            }
            if (Modifier.isProtected(f.getModifiers())) {
                visibility = Visibility.PROTECTED;
            }
            if (Modifier.isPublic(f.getModifiers())) {
                visibility = Visibility.PUBLIC;
            }

            return new UninitiatedField(
                    visibility,
                    new StatedReturnType(
                            new SpecifiedGenericType(
                                    JavaGenerics.specified(this, f.getGenericType())),
                            f.getType().isArray()),
                    f.getName(),
                    Modifier.isFinal(f.getModifiers()),
                    Modifier.isStatic(f.getModifiers()));
        }).collect(Collectors.toList());
    }

    @Override
    public Optional<SpecifiedGenerics> getExtendingClass() {
        return Optional.empty();
    }

    private List<SpecifiedGenerics> calcGenerics(Class<?> start) {
        if (start.getGenericInterfaces().length == 0) {
            return Collections.singletonList(new NoGenerics(new JavaClass(start)));
        }
        return Collections.singletonList(new JavaClassGenerics(new JavaClass(start)));
    }

    @Override
    public SortedSet<SpecifiedGenerics> getImplements() {
        return Stream
                .of(this.clazz.getInterfaces())
                .flatMap(i -> calcGenerics(i).stream())
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(g -> (IClass) g.getTargetReference()))));
    }

    @Override
    public Set<IClass> getNestedClasses() {
        return Stream.of(this.clazz.getClasses()).parallel().map(JavaClass::new).collect(Collectors.toSet());
    }

    @Override
    public Set<IFunction> getFunctions() {
        Set<IFunction> functions = Stream.of(this.clazz.getMethods()).map(JavaMethod::new).collect(Collectors.toSet());
        functions.addAll(Stream.of(this.clazz.getConstructors()).map(c -> new JavaConstructor(c)).collect(Collectors.toSet()));
        return functions;
    }

    public String writeCode() {
        return new JavaClassWriter().write(this);
    }

    @Override
    @Deprecated
    public @NotNull String writeCode(int indent) {
        return writeCode();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(this.clazz.getModifiers());
    }
}
