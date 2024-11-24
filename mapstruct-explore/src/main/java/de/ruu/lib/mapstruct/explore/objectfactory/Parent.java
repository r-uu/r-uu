package de.ruu.lib.mapstruct.explore.objectfactory;

import lombok.NonNull;

import java.util.Set;

public interface Parent<P extends Parent<P, C>,
                        C extends Child <P, C>>
{
	/**
	 * non-null but possibly empty set of children
	 *
	 * @return non-null set of children
	 */
	@NonNull Set<C> children();
}