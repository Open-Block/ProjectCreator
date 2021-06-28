package org.openblock.creator.code.variable.field;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.code.Visibility;
import org.openblock.creator.code.variable.IVariable;

public abstract class Field implements IVariable {

    protected String name;
    protected boolean isFinal;
    protected boolean isStatic;
    protected Visibility visibility;

    public Field(Visibility visibility, String name, boolean isFinal, boolean isStatic) {
        this.name = name;
        this.isFinal = isFinal;
        this.isStatic = isStatic;
        this.visibility = visibility;
    }

    public Visibility getVisibility(){
        return this.visibility;
    }

    public boolean isStatic(){
        return this.isStatic;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public boolean isFinal() {
        return this.isFinal;
    }
}
