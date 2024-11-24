module de.ruu.lib.archunit
{
	exports de.ruu.lib.archunit;

	requires transitive com.tngtech.archunit;
	requires org.slf4j;

	requires de.ruu.lib.util;

	requires static lombok;
}