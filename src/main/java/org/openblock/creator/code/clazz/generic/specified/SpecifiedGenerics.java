package org.openblock.creator.code.clazz.generic.specified;

import org.openblock.creator.code.Nameable;
import org.openblock.creator.code.clazz.generic.IGeneric;

import java.util.List;
import java.util.stream.Collectors;

public interface SpecifiedGenerics {

    List<IGeneric> getGenerics();

    List<SpecifiedGenerics> getSpecifiedGenericClass();

    Nameable getTargetReference();

    default String getDisplayName() {
        StringBuilder builder = new StringBuilder(this.getTargetReference().getName());
        List<SpecifiedGenerics> specifiedGenerics = this.getSpecifiedGenericClass();
        if (!specifiedGenerics.isEmpty()) {
            builder.append("<");
            builder.append(specifiedGenerics
                    .stream()
                    .map(SpecifiedGenerics::getDisplayName)
                    .collect(Collectors.joining(", ")));
            builder.append(">");
        }
        return builder.toString();
    }

}
