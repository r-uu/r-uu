package de.ruu.lib.util;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class IOTest
{
	@Test
//	@DisabledOnOs(WINDOWS)
	public void test()
	{
		String      input       = "äöüÄÖÜß";
		InputStream inputStream = new ByteArrayInputStream(input.getBytes());

		String output =
				new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));

		assertThat(output, is(equalTo(input)));
	}
}