package de.ruu.lib.mapstruct.explore.objectfactory;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Slf4j
@Getter                   // generate getter methods for all fields using lombok unless configured otherwise ({@code
                          // @Getter(AccessLevel.NONE}))
@Accessors(fluent = true) // generate fluent accessors with lombok and java-bean-style-accessors in non-abstract classes
                          // with ide, fluent accessors will (usually / by default) be ignored by mapstruct
abstract class ParentAbstract<
		P extends ParentAbstract<P, C>,
		C extends ChildAbstract <P, C>>
		extends PersonAbstract
	  implements Parent<P, C>
{
	// no java-bean-style accessor here, mapstruct will ignore fields without bean-style-accessor so mapping can be
	// controlled in beforeMapping
	@EqualsAndHashCode.Exclude
	private final Set<C> children = new HashSet<>();

	/** use this constructor to create regular objects */
	public ParentAbstract(@NonNull String name) { super(name); }
}