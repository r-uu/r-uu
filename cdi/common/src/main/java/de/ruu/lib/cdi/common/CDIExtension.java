package de.ruu.lib.cdi.common;

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.BeforeBeanDiscovery;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessAnnotatedType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CDIExtension implements Extension
{
	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd)
	{
		log.debug("beginning the scanning process");
	}

	<T> void processAnnotatedType(@Observes ProcessAnnotatedType<T> pat)
	{
		log.debug("scanning type: " + pat.getAnnotatedType().getJavaClass().getName());
	}

	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd)
	{
		log.debug("finished the scanning process");
	}
}