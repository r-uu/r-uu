package de.ruu.lib.fx.comp;

import javafx.fxml.FXML;

/**
 * @author r-uu
 */
public abstract class DefaultFXCViewController implements FXCViewController
{
	/**
	 * Default implementation throws {@link ExceptionInInitializerError}, override this to define
	 * an appropriate (if empty) behavior of <code>FXVComp</code> components.
	 */
	@FXML protected void initialize()
	{
		throw new ExceptionInInitializerError("interface default, you likely want to override this");
	}
}