package org.openblock.creator.impl.custom.clazz;

import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.clazz.ClassType;
import org.openblock.creator.impl.custom.clazz.interfacetype.CustomInterface;
import org.openblock.creator.impl.custom.function.CustomFunctionBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomClassBuilder {

    private String name;
    private String[] packageLocation;
    private boolean isFinal;
    private ClassType type;
    private Visibility visibility;

    private final List<CustomFunctionBuilder> functions = new ArrayList<>();

    public List<CustomFunctionBuilder> getFunctions() {
        return functions;
    }

    public CustomClassBuilder addFunctions(Collection<CustomFunctionBuilder> functions) {
        this.functions.addAll(functions);
        return this;
    }

    public CustomClassBuilder addFunctions(CustomFunctionBuilder... functions) {
        return this.addFunctions(Arrays.asList(functions));
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public CustomClassBuilder setVisibility(Visibility visibility) {
        this.visibility = visibility;
        return this;
    }

    public String getName() {
        return name;
    }

    public CustomClassBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public String[] getPackageLocation() {
        return packageLocation;
    }

    public CustomClassBuilder setPackageLocation(String... packageLocation) {
        this.packageLocation = packageLocation;
        return this;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public CustomClassBuilder setFinal(boolean aFinal) {
        isFinal = aFinal;
        return this;
    }

    public ClassType getType() {
        return type;
    }

    public CustomClassBuilder setType(ClassType type) {
        this.type = type;
        return this;
    }

    public AbstractCustomClass build() {
        switch (this.type) {
            case INTERFACE:
                return new CustomInterface(this);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
