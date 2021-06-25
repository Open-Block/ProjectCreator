package org.openblock.creator.code.clazz.type;

import org.openblock.creator.code.clazz.IClass;
import org.openblock.creator.code.clazz.generic.IGeneric;

import java.util.List;
import java.util.stream.Collectors;

public class GenericType implements IType {

    private final IGeneric generic;

    public GenericType(IGeneric generic) {
        this.generic = generic;
    }

    @Override
    public String getName() {
        return this.generic.getName();
    }

    @Override
    public List<IClass> getClasses() {
        return this.generic.getClasses().parallelStream().flatMap(t -> t.getClasses().parallelStream()).collect(Collectors.toList());
    }
}
