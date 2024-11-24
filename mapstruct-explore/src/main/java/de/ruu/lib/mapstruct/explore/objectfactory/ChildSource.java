package de.ruu.lib.mapstruct.explore.objectfactory;

import de.ruu.lib.mapstruct.BiMappedSource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChildSource<P extends ParentSource<C>,
                         C extends ChildSource<P, C>>
		extends ChildAbstract<P, C>
		implements BiMappedSource<ChildTarget<?, ?>>
{
	ChildSource(@NonNull P parent, @NonNull String name)
	{
		super(parent, name);
		parent.children().add(this);
	}

	void beforeMapping(@NonNull ChildTarget<?> target)
	{
		log.debug("before mapping starting");
		log.debug("before mapping finished");
	}

	void afterMapping(@NonNull ChildTarget<?> target)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull ChildTarget<?> toTarget()
	{
		return Mapper.INSTANCE.map(this);
	}
}
