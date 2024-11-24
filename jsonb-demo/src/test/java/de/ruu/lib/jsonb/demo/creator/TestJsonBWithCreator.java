package de.ruu.lib.jsonb.demo.creator;

import de.ruu.lib.jsonb.JsonbConfigurator;
import jakarta.json.bind.Jsonb;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
class TestJsonBWithCreator
{
	private final static Type SET_OF_PARENTS = new HashSet<ParentDTO>() { }.getClass().getGenericSuperclass();

	private final static int NUMBER_OF_PARENTS  = 3;
	private final static int NUMBER_OF_CHILDREN = 3;

	@Test void toJson()
	{
		log.debug("\nparents with children as json\n" +  getContext().toJson(createTestData()));
	}

	@Test void fromJson()
	{
		Set<ParentDTO> parentsWithChildrenIn = createTestData();

		Jsonb  jsonb = getContext();

		// serialise to json
		String jsonIn = jsonb.toJson(parentsWithChildrenIn);

		// deserialise from json
		Set<ParentDTO> parentsWithChildrenOut = jsonb.fromJson(jsonIn, SET_OF_PARENTS);

		// serialise deserialised objects again
		String jsonOut = jsonb.toJson(parentsWithChildrenOut);

		// compare two versions of serialised json
		assertThat(jsonOut, is(jsonIn));

		for (ParentDTO parentIn : parentsWithChildrenIn)
		{
			// find parentIn in parentsWithChildrenOut
			Optional<ParentDTO> optional =
					parentsWithChildrenOut
							.stream()
							.filter(p -> p.equals(parentIn))
							.findAny();
			assertThat(optional.isPresent(), is(true));

			ParentDTO parentOut = optional.get();

			// find each childIn in parentOut.children
			for (ChildDTO childIn : parentIn.getChildren())
			{
				Optional<ChildDTO> optionalInner =
						parentOut
								.getChildren()
								.stream()
								.filter(c -> c.equals(childIn))
								.findAny();
				assertThat(optionalInner.isPresent(), is(true));
			}
		}
	}

	private Jsonb getContext()
	{
		return new JsonbConfigurator().getContext();
	}

	private Set<ParentDTO> createTestData()
	{
		Set<ParentDTO> result = new HashSet<>();
		for (int i = 0; i < NUMBER_OF_PARENTS; i++)
		{
			ParentDTO parent = new ParentDTO((long) i, "" + i, new ArrayList<>());
			for (int j = 0; j < NUMBER_OF_CHILDREN; j++)
			{
				parent.getChildren().add(new ChildDTO((long) j, "c." + j));
			}
			result.add(parent);
		}
		return result;
	}
}