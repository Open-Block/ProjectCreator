package org.openblock.creator;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.regex.Pattern;

public class OpenBlockCreator {

    public static JavaClass getJavaClass(@NotNull String clazz) throws ClassNotFoundException {
        if (!(clazz.contains(Pattern.quote(".")) || clazz.contains("."))) {
            return new JavaClass(Class.forName("java.lang." + clazz));
        }
        return new JavaClass(Class.forName(clazz));
    }
}
