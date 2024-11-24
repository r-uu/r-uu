package de.ruu.lib.mapstruct.explore.objectfactory;

import de.ruu.lib.mapstruct.BiMappedTarget;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ParentTarget<C extends ChildTarget<? extends ParentTarget<C>, C>>
		extends ParentAbstract<C>
		implements BiMappedTarget<ParentSource<?>>
{
	/** use this constructor to create regular person objects */
	public ParentTarget(@NonNull String name) { super(name); }

	void beforeMapping(@NonNull ParentSource<C> input)
	{
		log.debug("before mapping starting");

		for (C child : input.children())
		{
			children.add(child.toTarget());
		}

		log.debug("before mapping finished");
	}

	void afterMapping(@NonNull ParentSource input)
	{
		log.debug("after mapping starting");
		log.debug("after mapping finished");
	}

	@Override public @NonNull ParentSource toSource() { return Mapper.INSTANCE.map(this); }
}