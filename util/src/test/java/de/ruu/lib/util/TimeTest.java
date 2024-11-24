package de.ruu.lib.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class TimeTest
{
	@Test void testSortableTimeStamp() { log.debug("sortable time stamp: {}", Time.getSortableTimestamp()); }
}