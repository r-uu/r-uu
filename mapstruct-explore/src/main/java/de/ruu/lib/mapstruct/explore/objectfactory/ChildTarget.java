package de.ruu.lib.mapstruct.explore.objectfactory;

import de.ruu.lib.mapstruct.BiMappedTarget;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChildTarget<P extends ParentTarget<C>,
                         C extends ChildTarget<P, C>>
		extends ChildAbstract<P, C>
		implements BiMappedTarget<ChildSource<?, ?>>
{
	ChildTarget(@NonNull P parent, @NonNull String name)
	{
		super(parent, name);
		parent.children().add(this);
	}

	void beforeMapping(@NonNull ChildSource<?> target)
	{
		log.debug("before mapping starting");
		log.debug("before mapping finished");
	}

	void afterMapping(@NonNull ChildSource<?> target)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull ChildSource<?> toSource() { return Mapper.INSTANCE.map(this); }
}