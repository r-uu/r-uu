package de.ruu.lib.jpa.core.mapstruct;

import de.ruu.lib.jpa.core.Entity;
import lombok.NonNull;

class SimpleAbstractMappedEntity extends AbstractMappedEntity<SimpleAbstractMappedDTO>
{
	void beforeMapping(@NonNull SimpleAbstractMappedDTO target) { }
	void afterMapping (@NonNull SimpleAbstractMappedDTO target) { }
	@Override public @NonNull SimpleAbstractMappedDTO toTarget()
	{
		return null;
	}

	@Override protected void afterMapping(@NonNull Entity<Long> input)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'afterMapping'");
	}
}
