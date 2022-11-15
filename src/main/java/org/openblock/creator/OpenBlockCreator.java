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
		if(clazz.equals("int")){
			return new JavaClass(int.class);
		}
		if(clazz.equals("double")){
			return new JavaClass(double.class);
		}
		if(clazz.equals("float")){
			return new JavaClass(float.class);
		}
		if(clazz.equals("byte")){
			return new JavaClass(byte.class);
		}
		if(clazz.equals("short")){
			return new JavaClass(short.class);
		}
		if(clazz.equals("long")){
			return new JavaClass(long.class);
		}
		if(clazz.equals("boolean")){
			return new JavaClass(boolean.class);
		}
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
