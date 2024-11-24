package de.ruu.lib.mapstruct.explore.objectfactory;

import de.ruu.lib.mapstruct.ReferenceCycleTracking;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Slf4j
@org.mapstruct.Mapper
abstract class Mapper
{
	static Mapper INSTANCE = Mappers.getMapper(Mapper.class);

	private static ReferenceCycleTracking CONTEXT  = new ReferenceCycleTracking();

	abstract ParentTarget map(ParentSource source);
	abstract ParentSource map(ParentTarget target);
	abstract ChildTarget  map(ChildSource  source);
	abstract ChildSource  map(ChildTarget  target);

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(ParentSource source, @MappingTarget ParentTarget target)
	{
		log.debug("source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(ParentTarget source, @MappingTarget ParentSource target)
	{
		log.debug("source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(ParentSource source, @MappingTarget ParentTarget target)
	{
		log.debug("source {}, target  {}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(ParentTarget source, @MappingTarget ParentSource target)
	{
		log.debug("source {}, target  {}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(ChildSource source, @MappingTarget ChildTarget target)
	{
		log.debug("source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@BeforeMapping void beforeMapping(ChildTarget source, @MappingTarget ChildSource target)
	{
		log.debug("source {}, target  {}", source, target);
		target.beforeMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(ChildSource source, @MappingTarget ChildTarget target)
	{
		log.debug("source {}, target  {}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	/** annotating parameter {@code target} with {@link MappingTarget} is essential for this method being called */
	@AfterMapping void afterMapping(ChildTarget source, @MappingTarget ChildSource target)
	{
		log.debug("source {}, target  {}", source, target);
		target.afterMapping(source); // invoke callback for mapping
	}

	@ObjectFactory @NonNull ParentSource lookupOrCreate(@NonNull ParentTarget input)
	{
		ParentSource result = CONTEXT.get(input, ParentSource.class);
		if (result == null)
		{
			result = new ParentSource(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory @NonNull ParentTarget lookupOrCreate(@NonNull ParentSource input)
	{
		ParentTarget result = CONTEXT.get(input, ParentTarget.class);
		if (result == null)
		{
			result = new ParentTarget(input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory @NonNull ChildSource lookupOrCreate(@NonNull ChildTarget input)
	{
		ChildSource result = CONTEXT.get(input, ChildSource.class);
		if (result == null)
		{
			result = new ChildSource(input.parent(), input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}

	@ObjectFactory @NonNull ChildTarget lookupOrCreate(@NonNull ChildSource input)
	{
		ChildTarget result = CONTEXT.get(input, ChildTarget.class);
		if (result == null)
		{
			result = new ChildTarget(input.parent(), input.name());
			CONTEXT.put(input, result);
			CONTEXT.put(result, input);
		}
		return result;
	}
}