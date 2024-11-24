package de.ruu.lib.jpa.se;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.persistence.EntityManager;

/**
 * IMPORTANT
 * <br>
 * DO NOT FORGET TO MENTION THIS <code>@Interceptor</code> TYPE IN <code>beans.xml</code>!!!
 * <br>
 * OR TO INITIALISE SeContainer LIKE THIS:
 * <pre>
 * {@code
 * SeContainer container =
 *     SeContainerInitializer
 *         .newInstance()
 *         .addBeanClasses    (TransactionalInterceptorCDI.class)
 *         .enableInterceptors(TransactionalInterceptorCDI.class)
 *         .selectAlternatives(MockGenerator.class)
 *         .initialize();
 * }
 * </pre>
 *
 * @author r-uu
 */
@Interceptor
@Priority(Interceptor.Priority.APPLICATION) // this makes the interceptor available in other modules / archives
@Transactional
public class TransactionalInterceptorCDI extends AbstractTransactionalInterceptor
{
	@Inject private EntityManager entityManager;

	@Override protected EntityManager entityManager() { return entityManager; }
}