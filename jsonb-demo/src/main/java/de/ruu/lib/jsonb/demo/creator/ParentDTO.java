package de.ruu.lib.jsonb.demo.creator;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/** data class for Jsonb tests, data classes have to be top-level and {@code public} */
@Getter
@EqualsAndHashCode
public class ParentDTO
{
	private Long           id;
	private String         field;
	@EqualsAndHashCode.Exclude
	private List<ChildDTO> children;

	/** constructor to be used by consumers */
	public ParentDTO(String field)
	{
		this();
		this.id    = ThreadLocalRandom.current().nextLong();
		this.field = field;
	}

	/** constructor to be used by JsonbCreator and tests */
	@JsonbCreator
	public ParentDTO(
			@JsonbProperty("id"      ) Long           id,
			@JsonbProperty("field"   ) String         field,
			@JsonbProperty("children") List<ChildDTO> children)
	{
		this(field);

		this.id       = id;
		this.children = children;

		children.forEach(c -> c.setParent(this));
	}

	// private no-args constructor to be used internally in order to initialise non-null fields, ...
	private ParentDTO() { children = new ArrayList<>(); }
}