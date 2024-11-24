package de.ruu.lib.jpa.core.mapstruct;

import lombok.NonNull;

class SimpleAbstractMappedDTO extends AbstractMappedDTO<SimpleAbstractMappedEntity>
{
	void beforeMapping(@NonNull SimpleAbstractMappedEntity source) { }
	void afterMapping (@NonNull SimpleAbstractMappedEntity source) { }

	@Override public @NonNull SimpleAbstractMappedEntity toSource()
	{
		return null;
	}
}