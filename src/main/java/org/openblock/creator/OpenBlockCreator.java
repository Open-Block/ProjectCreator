package org.openblock.creator;

import org.jetbrains.annotations.NotNull;
import org.openblock.creator.impl.java.clazz.JavaClass;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class OpenBlockCreator {

	private static final Collection<ClassLoader> CLASS_LOADERS = new LinkedBlockingQueue<>();

	public static Collection<ClassLoader> getDependingClassLoaders(){
		return Collections.unmodifiableCollection(CLASS_LOADERS);
	}

	public static void registerDependingClassLoader(@NotNull ClassLoader loader){
		CLASS_LOADERS.add(loader);
	}

	public static JavaClass getJavaClass(@NotNull String clazz) throws ClassNotFoundException {
		clazz = clazz.trim();
		if (!(clazz.contains(Pattern.quote(".")) || clazz.contains("."))) {
			return new JavaClass(Class.forName("java.lang." + clazz));
		}
		for(ClassLoader loader : CLASS_LOADERS){
			try{
				return new JavaClass(Class.forName(clazz, false, loader));
			}catch (Throwable ignored){

			}
		}


		return new JavaClass(Class.forName(clazz));
	}
}
