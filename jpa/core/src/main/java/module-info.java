module de.ruu.lib.jpa.core
{
	exports de.ruu.lib.jpa.core;
	exports de.ruu.lib.jpa.core.criteria;
	exports de.ruu.lib.jpa.core.criteria.restriction;

	opens   de.ruu.lib.jpa.core;

	requires jakarta.persistence;
	requires jakarta.annotation;
	requires java.sql;
	requires static lombok;
	requires de.ruu.lib.util;
	requires jakarta.json.bind;
}