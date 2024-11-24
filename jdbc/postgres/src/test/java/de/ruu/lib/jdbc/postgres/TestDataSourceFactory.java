package de.ruu.lib.jdbc.postgres;

import de.ruu.lib.junit.DisabledOnServerNotListening;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.ConfigProvider;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
class TestDataSourceFactory
{
	@DisabledOnServerNotListening(propertyNameHost = "database_host", propertyNamePort = "database_port")
	@Test void testDataSourceFactory() throws SQLException
	{
		String databaseHost =
				ConfigProvider
						.getConfig()
						.getOptionalValue("database_host", String .class)
						.orElse("localhost");
		int    databasePort =
				ConfigProvider
						.getConfig()
						.getOptionalValue("database_port", Integer.class)
						.orElse(5432);
		String databaseName =
				ConfigProvider
						.getConfig()
						.getOptionalValue("database_name", String.class)
						.orElse("lib_test");
		String databaseUser =
				ConfigProvider
						.getConfig()
						.getOptionalValue("database_user", String.class)
						.orElse("lib_test");
		String databasePass =
				ConfigProvider
						.getConfig()
						.getOptionalValue("database_pass", String.class)
						.orElse("lib_test");

		JDBCURL jdbcURL = new JDBCURL(databaseHost, databasePort, databaseName);

		log.debug("jdbc url: {}", jdbcURL);

		DataSourceFactory dataSourceFactory = new DataSourceFactory(jdbcURL, databaseUser, databasePass);
		DataSource        dataSource        = dataSourceFactory.create();

		assertThat(dataSource,                 is(not(nullValue())));
		assertThat(dataSource.getConnection(), is(not(nullValue())));
	}
}