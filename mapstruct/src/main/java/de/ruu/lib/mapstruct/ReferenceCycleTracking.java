package de.ruu.lib.mapstruct;

import de.ruu.lib.util.bimapped.BiMap;
import lombok.NonNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

/**
 * Used as {@link org.mapstruct.Context} parameter in mapstruct mappings, leverages mapstruct annotations
 * {@link BeforeMapping}, {@link AfterMapping}, {@link TargetType} and {@link MappingTarget} for methods.
 */
public class ReferenceCycleTracking extends BiMap
{
	@BeforeMapping
	@Override
	public <T> T get(@NonNull Object source, @TargetType @NonNull Class<T> targetType)
	{
		return super.get(source, targetType);
	}

	/**
	 * Annotated with {@link BeforeMapping} to make sure, {@code source} and {@code target} are available in the context
	 * as soon as possible.
	 *
	 * @param source
	 * @param target
	 */
	@BeforeMapping
	@Override
	public void put(@NonNull Object source, @MappingTarget @NonNull Object target)
	{
		super.put(source, target);
	}
}