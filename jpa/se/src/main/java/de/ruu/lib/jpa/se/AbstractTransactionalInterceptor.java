package de.ruu.lib.jpa.se;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTransactionalInterceptor
{
	protected abstract EntityManager entityManager();

	@AroundInvoke
	public Object transaction(InvocationContext context) throws Exception
	{
		Object result;

		String methodName =
				context.getMethod().getDeclaringClass().getName() + "." + context.getMethod().getName();

		EntityTransaction transaction = entityManager().getTransaction();

		log.trace(
				"\nentity manager {}, transaction {}", entityManager().hashCode(), transaction.hashCode());

		boolean startedTransaction = false;

		try
		{
			if (transaction.isActive())
			{
				log.debug("resuming active transaction {}", transaction);
			}
			else
			{
				log.debug("starting transaction {}", transaction);
				transaction.begin();
				startedTransaction = true;
			}

			log.trace("calling           {} in transaction {}", methodName, transaction);
			result = context.proceed();
			log.trace("returned from     {} in transaction {}", methodName, transaction);
		}
		catch (Throwable t)
		{
			log.error("failure executing {} in transaction {}", methodName, transaction, t);
			throw t;
		}
		finally
		{
			if (startedTransaction)
			{
				if (transaction.isActive())
				{
					try
					{
						log.debug("committing transaction {}", transaction);
						transaction.commit();
						log.debug("commit succeeded, transaction {}", transaction);
					}
					catch (RuntimeException e)
					{
						log.debug("rolling back transaction {}", transaction);
						transaction.rollback();
						log.debug("rollback succeeded, transaction {}", transaction);
					}
				}
				else
				{
					log.warn("can't commit inactive transaction {}", transaction);
				}
			}
			else
			{
				log.trace("resuming transaction {}", transaction);
			}
		}

		return result;
	}
}