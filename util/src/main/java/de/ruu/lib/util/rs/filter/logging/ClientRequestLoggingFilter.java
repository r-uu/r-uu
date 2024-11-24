package de.ruu.lib.util.rs.filter.logging;

import java.io.IOException;

import jakarta.ws.rs.client.ClientRequestContext;
import jakarta.ws.rs.client.ClientRequestFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientRequestLoggingFilter implements ClientRequestFilter
{
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException
	{
		log.debug("request URL {}", requestContext.getUri().toURL().toString());
//		Set<String> keySet = new TreeSet<>(requestContext.getHeaders().keySet());
//		for (String key : keySet)
//		{
//			log.debug("request header {}, {}", key, requestContext.getHeaders().lookup(key));
//		}
	}
}