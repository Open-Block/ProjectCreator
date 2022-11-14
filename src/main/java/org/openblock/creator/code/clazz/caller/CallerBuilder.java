package org.openblock.creator.code.clazz.caller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.openblock.creator.code.CodeBuilder;
import org.openblock.creator.code.Codeable;
import org.openblock.creator.code.call.Callable;
import org.openblock.creator.code.clazz.caller.thiscaller.ThisCallable;
import org.openblock.creator.code.clazz.caller.thiscaller.ThisCaller;
import org.openblock.creator.code.function.IConstructor;
import org.openblock.creator.code.function.IMethod;
import org.openblock.creator.code.function.caller.ConstructorCaller;
import org.openblock.creator.code.function.caller.MethodCaller;
import org.openblock.creator.code.variable.IVariable;
import org.openblock.creator.code.variable.VariableCaller;

public class CallerBuilder implements CodeBuilder<CallerBuilder> {

	private Callable callable;

	public CallerBuilder setCaller(@NotNull Callable callable) {
		this.callable = callable;
		return this;
	}

	public @Nullable Callable getCallable() {
		return this.callable;
	}

	@Override
	public Codeable build() {
		if (this.callable instanceof ThisCallable thisCallable) {
			return new ThisCaller(thisCallable);
		}
		if (this.callable instanceof IVariable vari) {
			return new VariableCaller<>(vari);
		}
		if(this.callable instanceof IMethod method){
			return new MethodCaller(method);
		}
		if(this.callable instanceof IConstructor constructor){
			return new ConstructorCaller(constructor);
		}
		throw new IllegalStateException("Callable must be set before building");
	}
}
