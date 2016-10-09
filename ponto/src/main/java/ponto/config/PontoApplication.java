package ponto.config;

import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ponto")
public class PontoApplication extends SpringBootServletInitializer {

	private static final Logger LOG = LogManager
			.getLogger(PontoApplication.class);

	public static void main(String[] args) throws Exception {

		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		Locale.setDefault(new Locale("pt", "BR"));

		final long start = System.nanoTime();
		SpringApplication.run(PontoApplication.class, args);
		final long end = System.nanoTime();
		LOG.warn("Ponto inicializado em: "
				+ TimeUnit.SECONDS.convert(((end - start) / 1000000),
						TimeUnit.MILLISECONDS) + " s");
	}

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(PontoApplication.class);
	}

	/**
	 * Destruindo o Hsqldb para prevenir o Memory Leak do servidor (No caso o
	 * Tomcat)
	 * 
	 * @return
	 */
	// @Bean
	// protected ServletContextListener listener() {
	//
	// return new ServletContextListener() {
	//
	// @Override
	// public void contextInitialized(ServletContextEvent sce) {
	// LOG.info("Inicializando Contexto...");
	// }
	//
	// @Override
	// public final void contextDestroyed(ServletContextEvent sce) {
	//
	// LOG.info("Destruindo Contexto...");
	//
	// try {
	// LOG.info("Calling MySQL AbandonedConnectionCleanupThread shutdown");
	// com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();
	//
	// } catch (InterruptedException e) {
	// LOG.error(
	// "Error calling MySQL AbandonedConnectionCleanupThread shutdown {}",
	// e);
	// }
	//
	// ClassLoader cl = Thread.currentThread().getContextClassLoader();
	//
	// Enumeration<Driver> drivers = DriverManager.getDrivers();
	// while (drivers.hasMoreElements()) {
	// Driver driver = drivers.nextElement();
	//
	// if (driver.getClass().getClassLoader() == cl) {
	//
	// try {
	// LOG.info("Deregistering JDBC driver {}");
	// DriverManager.deregisterDriver(driver);
	//
	// } catch (SQLException ex) {
	// LOG.error("Error deregistering JDBC driver {}");
	// }
	//
	// } else {
	// LOG.trace("Not deregistering JDBC driver {} as it does not belong to this
	// webapp's ClassLoader");
	// }
	// }
	// }
	// };
	// }

}
