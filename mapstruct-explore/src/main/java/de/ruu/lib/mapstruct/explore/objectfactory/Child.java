package de.ruu.lib.mapstruct.explore.objectfactory;

import java.util.Optional;

public interface Child<P extends Parent<P, C>,
                       C extends Child <P, C>>
{
	/**
	 * "root" children do not have a parent, we ignore that natural children usually have two parents (mother and father)
	 *
	 * @return optional single parent of {@code this} child
	 */
	Optional<P> parent();
}