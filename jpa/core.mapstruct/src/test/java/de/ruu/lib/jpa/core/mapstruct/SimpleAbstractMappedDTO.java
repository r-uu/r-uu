package de.ruu.lib.jpa.core.mapstruct;

import lombok.NonNull;

class SimpleAbstractMappedDTO extends AbstractMappedDTO<SimpleAbstractMappedEntity>
{
	@Override public void beforeMapping(@NonNull SimpleAbstractMappedEntity source) { }
	@Override public void afterMapping (@NonNull SimpleAbstractMappedEntity source) { }

	@Override public @NonNull SimpleAbstractMappedEntity toSource()
	{
		return null;
	}
}