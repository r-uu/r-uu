package de.ruu.lib.jpa.se.hibernate;

import static org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_PASSWORD;
import static org.hibernate.cfg.AvailableSettings.JAKARTA_JDBC_USER;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;

@RequiredArgsConstructor
public class EntityManagerFactoryProducer
{
	private final PersistenceUnitInfo       persistenceUnitInfo;
	private final PersistenceUnitProperties persistenceUnitProperties;

	public EntityManagerFactory produce(String username, String password)
	{
		Map<String, Object> properties = new HashMap<>(persistenceUnitProperties.properties());

		properties.put(JAKARTA_JDBC_USER,     username);
		properties.put(JAKARTA_JDBC_PASSWORD, password);

		return
//				new HibernatePersistenceProvider()
//						.createContainerEntityManagerFactory(persistenceUnitInfo, persistenceUnitProperties.properties());
				new HibernatePersistenceProvider()
						.createContainerEntityManagerFactory(persistenceUnitInfo, properties);
//				new HibernatePersistenceProvider()
//						.createEntityManagerFactory(persistenceUnitInfo.getPersistenceUnitName(), properties);
//				Persistence
//						.createEntityManagerFactory(persistenceUnitInfo.getPersistenceUnitName(), properties);
	}
}