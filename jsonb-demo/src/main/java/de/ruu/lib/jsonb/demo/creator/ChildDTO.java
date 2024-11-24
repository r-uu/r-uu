package de.ruu.lib.jsonb.demo.creator;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.util.concurrent.ThreadLocalRandom;

/**
 * data class for Jsonb tests
 * <p>
 * data classes have to be top-level, {@code public} and equipped with a {@code public} no-args default constructor
 *
 * @author ruu
 */
@Getter
@EqualsAndHashCode
public class ChildDTO
{
	private Long      id;
	@JsonbTransient
	@EqualsAndHashCode.Exclude
	private ParentDTO parent;
	private String    field;

	/** constructor to be used by consumers */
	ChildDTO(ParentDTO parent, String field)
	{
		this();
		this.parent = parent;
		this.field  = field;
	}

	/** constructor to be used by JsonbCreator and tests */
	@JsonbCreator
	public ChildDTO(
			@JsonbProperty("id"   ) Long   id,
			@JsonbProperty("field") String field)
	{
		this.id    = id;
		this.field = field;
	}

	/** private constructor to be used internally in order to initialise non-null fields, ... */
	private ChildDTO() { id = ThreadLocalRandom.current().nextLong(); }

	/** package visible method to be called from JsonbCreator in {@link ParentDTO} */
	void setParent(@NonNull ParentDTO parent) { this.parent = parent; }
}