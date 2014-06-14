package carrental.web.controllers;

import static carrental.web.tags.SetLocale.DEFAULT_L10N_BUNDLE_PROVIDER_NAME;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.jdbc.core.JdbcTemplate;

import carrental.repository.FileLoader;
import carrental.repository.OrderHistoryDao;
import carrental.repository.impl.OrderDaoImpl;
import carrental.repository.impl.OrderHistoryImpl;
import carrental.repository.impl.PassportInfoDaoImpl;
import carrental.repository.impl.UserDaoImpl;
import carrental.repository.impl.VehicleDaoImpl;
import carrental.web.controllers.actions.ActionFactory;
import carrental.web.controllers.actions.ActionFactoryImpl;
import carrental.web.i18n.L10nBundleProvider;
import carrental.web.i18n.impl.TrivialL10nBundleProvider;
import carrental.web.validators.Validator;

import com.jolbox.bonecp.BoneCPDataSource;

@WebListener
public class ContextInitWithActionFactory implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		String dbCreationQueriesFile = ctx.getInitParameter("dbCreationQueriesFile");
		String boneCpPropertiesFile = ctx.getInitParameter("boneCpPropertiesFile");
		String longQueriesFile = ctx.getInitParameter("longQueriesFile");
		Properties longQueries = FileLoader.loadPropertiesFromClasspath(longQueriesFile);

		BoneCPDataSource dataSource = new BoneCPDataSource();
		Properties properties = FileLoader.loadPropertiesFromClasspath(boneCpPropertiesFile);
		try {
			Class.forName(properties.getProperty("driver"));
			dataSource.setProperties(properties);
			InputStream queriesInputStream = FileLoader.getResourceAsStream(dbCreationQueriesFile);
			FileLoader.executeQueries(queriesInputStream, dataSource);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		OrderDaoImpl orderDao = new OrderDaoImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		VehicleDaoImpl vehicleDao = new VehicleDaoImpl();
		OrderHistoryDao orderHistoryDao = new OrderHistoryImpl(jdbcTemplate);
		PassportInfoDaoImpl passportInfoDaoImpl = new PassportInfoDaoImpl();
		passportInfoDaoImpl.setJdbcTemplate(jdbcTemplate);

		vehicleDao.setLongQueries(longQueries);
		orderDao.setJdbcTemplate(jdbcTemplate);
		userDao.setJdbcTemplate(jdbcTemplate);
		vehicleDao.setJdbcTemplate(jdbcTemplate);
		
		Map<String, Object> fields = new HashMap<>();
		fields.put("vehicleDao", vehicleDao);
		fields.put("orderDao", orderDao);
		fields.put("userDao", userDao);
		fields.put("validator", new Validator());
		fields.put("orderHistoryDao", orderHistoryDao);
		fields.put("passportInfoDao", passportInfoDaoImpl);

		try {
			L10nBundleProvider l10nBundleProvider = new TrivialL10nBundleProvider();
			ctx.setAttribute(DEFAULT_L10N_BUNDLE_PROVIDER_NAME, l10nBundleProvider);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		try {
			ActionFactory actionFactory = new ActionFactoryImpl(fields);
			ctx.setAttribute("actionFactory", actionFactory);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		PropertyConfigurator.configure(FileLoader.getResourceAsStream("log4j.properties"));

	}

}
