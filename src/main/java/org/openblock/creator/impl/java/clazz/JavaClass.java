package org.openblock.creator.impl.java.clazz;

import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;
import org.openblock.creator.code.clazz.generic.SpecifiedGenerics;
import org.openblock.creator.code.function.IFunction;
import org.openblock.creator.impl.java.clazz.generic.JavaClassSpecifiedGenerics;
import org.openblock.creator.impl.java.clazz.writer.JavaClassWriter;
import org.openblock.creator.impl.java.function.method.JavaMethod;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaClass implements IClass {

    private final Class<?> clazz;

    public JavaClass(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Class<?> getTargetClass() {
        return this.clazz;
    }

    @Override
    public String getName() {
        return this.clazz.getSimpleName();
    }

    @Override
    public String[] getPackage() {
        return this.clazz.getPackage().getName().split(Pattern.quote("."));
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(this.clazz.getModifiers());
    }

    @Override
    public ClassType getClassType() {
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
    public Visibility getVisibility() {
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
        return Collections.emptyList();
    }

    @Override
    public Optional<SpecifiedGenerics> getExtendingClass() {
        return Optional.empty();
    }

    @Override
    public SortedSet<SpecifiedGenerics> getImplements() {
        return Stream
                .of(this.clazz.getInterfaces())
                .map(JavaClassSpecifiedGenerics::new)
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(g -> (IClass) g.getTargetReference()))));
    }

    @Override
    public Set<IClass> getNestedClasses() {
        return Stream.of(this.clazz.getClasses()).parallel().map(JavaClass::new).collect(Collectors.toSet());
    }

    @Override
    public Set<IFunction> getFunctions() {
        return Stream.of(this.clazz.getMethods()).map(JavaMethod::new).collect(Collectors.toSet());
    }

    @Override
    public String writeCode() {
        return new JavaClassWriter().write(this);
    }

    @Override
    public SortedSet<IClass> getImports() {
        return Collections.emptySortedSet();
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(this.clazz.getModifiers());
    }
}
