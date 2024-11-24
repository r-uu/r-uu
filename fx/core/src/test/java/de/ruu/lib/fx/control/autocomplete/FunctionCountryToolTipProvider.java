package de.ruu.lib.fx.control.autocomplete;

import java.util.function.Function;

public abstract class FunctionCountryToolTipProvider
{
	public final static Function<Country, String> FUNCTION_TOOL_TIP_PROVIDER =
			country -> country.getName();
}