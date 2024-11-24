package de.ruu.lib.util.rs.filter.logging;

import java.io.IOException;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Filtered @Provider
public class ContainerFilter implements ContainerRequestFilter, ContainerResponseFilter
{
	@Override public void filter(ContainerRequestContext reqCtx, ContainerResponseContext resCtx) throws IOException { }

	@Override public void filter(ContainerRequestContext reqCtx) throws IOException { }
}