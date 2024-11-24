package de.ruu.lib.fx.comp;

import de.ruu.lib.fx.FXUtil;
import jakarta.enterprise.inject.spi.CDI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Optional;

import static de.ruu.lib.util.BooleanFunctions.not;
import static java.util.Objects.isNull;

/**
 * Abstract base class for JavaFX visual components (<code>FXVComp</code> views).
 * <p>
 * Implementations of {@link DefaultFXCView}s can be run and tested as independent JavaFX
 * applications using {@link FXCApp} and {@link FXCAppRunner}.
 * <p>
 * To facilitate easy integration in large JavaFX applications {@link FXCView} provides:
 * <ul>
 *   <li><code>protected</code> access to the controller that defines the behavior of the view and implements the
 *       services of the component.</li>
 *   <li><code>protected</code> access to various methods that allow to override default
 *       naming conventions if they do not fit the actual needs for a component.</li>
 * </ul>
 *
 * @author r-uu
 */
@Slf4j
public abstract class DefaultFXCView implements FXCView
{
	private Parent localRoot;

	private FXCViewService service;

	private Optional<? extends FXCViewController> controllerOptional;

	private Scene scene;

	private String fxmlResourceName;

	private String cssResourceName;

	/**
	 * Loads the component's tree of nodes from an <code>.fxml</code> file. It looks for the
	 * file by leveraging the <code>FXVComp</code> default naming conventions (see
	 * {@link de.ruu.lib.fx.comp}) or the overridden return value from {@link
	 * #getFXLMResourceName()}
	 */
	@Override public Parent getLocalRoot()
	{
		if (not(isNull(localRoot))) return localRoot;

		final FXMLLoader fxmlLoader = createFXMLLoader();

		localRoot = FXUtil.loadFrom(fxmlLoader);

		return localRoot;
	}

	@Override public FXCViewService getService()
	{
		if (not(isNull(service))) return service;

		final Class<? extends FXCViewService> serviceClass = getServiceClass();

		service = CDI.current().select(serviceClass).get();

		return service;
	}

	protected Optional<? extends FXCViewController> getController()
	{
		if (not(isNull(controllerOptional))) return controllerOptional;

		final Optional<Class<? extends FXCViewController>> controllerClass = getControllerClass();

		if (controllerClass.isPresent())
		{
			// use CDI to instantiate controller
			FXCViewController fxcViewController = CDI.current().select(controllerClass.get()).get();

			// if controller implements service interface set service to controller instance
			if (fxcViewController instanceof FXCViewService) service = (FXCViewService) fxcViewController;

			controllerOptional = Optional.of(fxcViewController);

			log.debug("using " + controllerOptional.get().getClass().getName() + " controller");
		}
		else
		{
			controllerOptional = Optional.empty();
		}

		return controllerOptional;
	}

	private FXMLLoader createFXMLLoader()
	{
		final FXMLLoader fxmlLoader = new FXMLLoader();

		// configure fxmlLoader
		//   find and set location
		final String fxmlResourceName = getFXLMResourceName();
		final URL    fxmlLocation     = getClass().getResource(fxmlResourceName);

		if (fxmlLocation == null)
		{
			log.error(
					"no resource exists for {} at location {}, does module {} open package {}?",
					getClass().getName(),
					fxmlResourceName,
					getClass().getModule().getName(),
					getClass().getPackage().getName());
		}
		else
		{
			log.debug(
					"configured {} to load fxml from {}",
					FXMLLoader.class.getName(),
					fxmlLocation.toExternalForm());
			fxmlLoader.setLocation(fxmlLocation);
		}

		final Object controllerFromFXML = fxmlLoader.getController();

		if (controllerFromFXML != null)
		{
			log.warn("found {} controller, controller might be replaced", controllerFromFXML.getClass().getName());
		}

		//   set controller
		getController().ifPresent(controller -> fxmlLoader.setController(controller));

		return fxmlLoader;
	}

	protected Scene getScene()
	{
		if (scene != null) { return scene; }
		scene = new Scene(getLocalRoot());
		addStylesheet(scene);
		return scene;
	}

	protected void addStylesheet(final Scene scene)
	{
		final String resourceName = getCSSResourceName();
		final URL resource = getClass().getResource(resourceName);

		if (null == resource)
		{
			log.warn("no resource exists at " + resourceName);
			return;
		}

		final String externalForm = resource.toExternalForm();
		scene.getStylesheets().add(externalForm);
		log.debug("added stylesheet " + externalForm + " to " + getClass().getName() + " scene");
	}

	/** @return <code>.fxml</code> resource file name based on default naming convention */
	protected String getFXLMResourceName()
	{
		if (null == fxmlResourceName) { fxmlResourceName = getClass().getSimpleName() + ".fxml"; }
		return fxmlResourceName;
	}

	/** @return <code>.css</code> resource file name based on default naming convention */
	protected String getCSSResourceName()
	{
		if (null == cssResourceName) { cssResourceName = getClass().getSimpleName() + ".css"; }
		return cssResourceName;
	}

	@SuppressWarnings("unchecked")
	protected Class<? extends FXCViewService> getServiceClass()
	{
		Class<? extends FXCViewService> klass = null;
		final String serviceClassName = getServiceClassName();

		try
		{
			klass = (Class< ? extends FXCViewService>) Class.forName(serviceClassName);
			if (not(FXCViewService.class.isAssignableFrom(klass)))
			{
				throw new UnsupportedOperationException(klass.getName() + " is not a " + FXCViewService.class.getName());
			}
		}
		catch (final ClassNotFoundException e)
		{
			throw new IllegalStateException("could not find class " + serviceClassName, e);
		}

		return klass;
	}

	@SuppressWarnings("unchecked")
	protected Optional<Class<? extends FXCViewController>> getControllerClass()
	{
		Class<?> klass = null;
		final String controllerClassName = getControllerClassName();

		try
		{
			klass = Class.forName(controllerClassName);
		}
		catch (final ClassNotFoundException e)
		{
			log.error("could not find class " + controllerClassName, e);
			return Optional.empty();
		}

		return Optional.of((Class<? extends FXCViewController>) klass);
	}

	/**
	 * @return {@link FXCViewService} class name that by default is the same as to the name of the current
	 *         class plus a trailing "Service". This complies to the naming conventions.
	 */
	protected String getServiceClassName() { return getClass().getName() + "Service"; }

	/**
	 * @return {@link FXCViewController} class name that by default is the same as to the name of the current
	 *         class plus a trailing "Controller". This complies to the naming conventions.
	 */
	protected String getControllerClassName() { return getClass().getName() + "Controller"; }

	/**
	 * @return {@link FXCApp} class name that by default is the same as to the name of the current
	 *         class plus a trailing "App". This complies to the naming conventions.
	 */
	protected String getClassNameApp() { return getClass().getName() + "App"; }
}