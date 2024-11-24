package de.ruu.lib.util;

import org.junit.jupiter.api.Test;

import static de.ruu.lib.util.BooleanFunctions.is;
import static de.ruu.lib.util.BooleanFunctions.isNot;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BooleanFunctionsTest
{
	@Test void testIsForTrueIsTrue    () { assertTrue (is   (true )); }
	@Test void testIsForFalseIsFalse  () { assertFalse(is   (false)); }
	@Test void testIsNotForTrueIsFalse() { assertFalse(isNot(true )); }
	@Test void testIsNotForFalseIsTrue() { assertTrue (isNot(false)); }
}