package de.ruu.lib.mapstruct.explore.objectfactory;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static de.ruu.lib.mapstruct.explore.objectfactory.Mapper.INSTANCE;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
class MapperTest
{
	@Test void test()
	{
		String name = "map me";

		// map
		ParentSource parentSource = new ParentSource(name);
		ParentTarget parentTarget = INSTANCE.map(parentSource);

		assertThat(parentTarget       , is(not(nullValue())));
		assertThat(parentTarget.name(), is(parentSource.name()));
		assertThat(parentTarget.name(), is(name));

		// remap
		ParentSource remappedParentSource = INSTANCE.map(parentTarget);

		assertThat(remappedParentSource       , is(not(nullValue())));
		assertThat(remappedParentSource.name(), is(parentTarget.name()));
		assertThat(remappedParentSource.name(), is(name));
		assertThat(remappedParentSource       , is(parentSource));

		// add children
		int childCount = 3;
		for (int i = 0; i < childCount; i++)
		{
			ChildSource child = new ChildSource(parentSource, "" + i);
		}

		// map
		parentTarget = parentSource.toTarget();

		assertThat(parentTarget                  , is(not(nullValue())));
		assertThat(parentTarget.name()           , is(parentSource.name()));
		assertThat(parentTarget.name()           , is(name));
		assertThat(parentTarget.children().size(), is(childCount));

		// remap
		remappedParentSource = INSTANCE.map(parentTarget);

		assertThat(remappedParentSource                  , is(not(nullValue())));
		assertThat(remappedParentSource.name()           , is(parentTarget.name()));
		assertThat(remappedParentSource.name()           , is(name));
		assertThat(remappedParentSource.children().size(), is(childCount));
		assertThat(remappedParentSource                  , is(parentSource));

		log.debug("parent source {}", parentSource);
		log.debug("parent target {}", parentTarget);
	}
}