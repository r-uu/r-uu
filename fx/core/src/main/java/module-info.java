module de.ruu.lib.fx.core
{
	exports de.ruu.lib.fx;
	exports de.ruu.lib.fx.control;
	exports de.ruu.lib.fx.control.autocomplete;
	exports de.ruu.lib.fx.control.autocomplete.textfield;
	exports de.ruu.lib.fx.control.dialog;
	exports de.ruu.lib.fx.control.textfield.number;
	exports de.ruu.lib.fx.css;

	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires static lombok;
	requires org.slf4j;

	requires de.ruu.lib.util;
}