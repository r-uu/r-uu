package de.ruu.lib.jpa.core.mapstruct;

import lombok.NonNull;

class SimpleAbstractMappedEntity extends AbstractMappedEntity<SimpleAbstractMappedDTO>
{
	@Override public void beforeMapping(@NonNull SimpleAbstractMappedDTO target) { }
	@Override public void afterMapping (@NonNull SimpleAbstractMappedDTO target) { }
	@Override public @NonNull SimpleAbstractMappedDTO toTarget()
	{
		return null;
	}
}
