package carrental.repository.impl;

import static carrental.repository.jdbc.DaoUtils.assertNotNull;
import static carrental.repository.jdbc.DaoUtils.assertOnlyOneRowAffected;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import carrental.domain.Vehicle;
import carrental.repository.VehicleDao;

import com.almende.util.IntervalsUtil;

@Repository
public class VehicleDaoImpl implements VehicleDao {

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final String SQL_CREATE =
			"INSERT INTO Vehicles (model, mileage, year) VALUES (?, ?, ?)";
	private static final String SQL_FIND_BY_ID =
			"SELECT model, mileage, year FROM Vehicles WHERE vehicleId = ?";
	private static final String SQL_FIND_WHEN_BOOKED =
			"SELECT startTime, endTime FROM Orders WHERE vehicleId = ?";
	private static final String SQL_UPDATE =
			"UPDATE Vehicles SET model = ?, mileage = ?, year = ? WHERE vehicleId = ?";
	private static final String SQL_DELETE =
			"DELETE FROM Vehicles WHERE vehicleId = ?";
	private static final String SQL_COUNT =
			"SELECT COUNT(vehicleId) FROM Vehicles";
	private static final String SQL_LIST =
			"SELECT model, mileage, year, vehicleId FROM Vehicles";
	private static final String SQL_LIST_PARTIAL =
			SQL_LIST + " WHERE vehicleId >= ? ORDER BY vehicleId LIMIT ?";
	public static final long MILLIS_IN_DAY = 86_400_000;
	public static final long BOOKING_LIMIT = 40 * MILLIS_IN_DAY;

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private JdbcTemplate jdbcTemplate;
	private Properties longQueries;
	private RowMapper<Vehicle> vehicleRowMapper = new RowMapper<Vehicle>() {
		@Override
		public Vehicle mapRow(ResultSet rSet, int arg1) throws SQLException {
			return new Vehicle(rSet.getString(1), rSet.getInt(2), rSet.getInt(3), rSet.getInt(4));
		}
	};

	// Setters
	// ----------------------------------------------------------------------------------------------------
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public void setLongQueries(Properties longQueries) {
		this.longQueries = longQueries;
	}

	// Create methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void create(final Vehicle vehicle) throws IllegalArgumentException {
		if (vehicle.getVehicleId() != null)
			throw new IllegalArgumentException("You are not allowed to save a vehicle with a non-null id");
		KeyHolder holder = new GeneratedKeyHolder();
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, vehicle.getModel());
				ps.setInt(2, vehicle.getMileage());
				ps.setInt(3, vehicle.getYear());
				return ps;
			}
		}, holder));
		vehicle.setVehicleId(holder.getKey().intValue());
	}

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public Vehicle find(final Integer vehicleId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement ps = arg0.prepareStatement(SQL_FIND_BY_ID);
				ps.setInt(1, vehicleId);
				return ps;
			}
		}, new ResultSetExtractor<Vehicle>() {
			@Override
			public Vehicle extractData(ResultSet rSet) throws SQLException, DataAccessException {
				return rSet.next() ? new Vehicle(
						rSet.getString(1),
						rSet.getInt(2),
						rSet.getInt(3),
						vehicleId) : null;
			}
		});
	}

	@Override
	public List<Vehicle> findAllAvailable(final Interval interval) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = longQueries.getProperty("vehicleDao.findAllAvailable");
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setLong(1, interval.getEndMillis());
				ps.setLong(2, interval.getStartMillis());
				return ps;
			}
		}, vehicleRowMapper);
	}

	@Override
	public List<Interval> findWhenAvailable(Integer vehicleId) throws IllegalArgumentException {
		long currentTime = System.currentTimeMillis();
		return IntervalsUtil.inverse(
				findWhenBooked(vehicleId),
				new DateTime(currentTime),
				new DateTime(currentTime + BOOKING_LIMIT));
	}

	@Override
	public List<Interval> findWhenBooked(final Integer vehicleId) throws IllegalArgumentException {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_FIND_WHEN_BOOKED);
				ps.setInt(1, vehicleId);
				return ps;
			}
		}, new RowMapper<Interval>() {
			@Override
			public Interval mapRow(ResultSet rSet, int arg1) throws SQLException {
				return new Interval(rSet.getLong(1), rSet.getLong(2));
			}
		});
	}

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void update(final Vehicle vehicle) throws IllegalArgumentException {
		final Integer vehicleId = vehicle.getVehicleId();
		assertNotNull(vehicleId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection
						.prepareStatement(SQL_UPDATE);
				ps.setString(1, vehicle.getModel());
				ps.setInt(2, vehicle.getMileage());
				ps.setInt(3, vehicle.getYear());
				ps.setInt(4, vehicleId);
				return ps;
			}
		}));

	}

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void delete(Vehicle vehicle) throws IllegalArgumentException {
		delete(vehicle.getVehicleId());
		vehicle.setVehicleId(null);
	}

	@Override
	public void delete(final Integer vehicleId) throws IllegalArgumentException {
		assertNotNull(vehicleId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
				ps.setInt(1, vehicleId);
				return ps;
			}
		}));

	}

	// List methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public int getVehicleCounter() {
		return jdbcTemplate.query(SQL_COUNT, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rSet) throws SQLException, DataAccessException {
				rSet.next();
				return rSet.getInt(1);
			}
		});
	}

	@Override
	public List<Vehicle> list() {
		return jdbcTemplate.query(SQL_LIST, vehicleRowMapper);
	}

	@Override
	public List<Vehicle> list(final Integer startId, final int howMany) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_LIST_PARTIAL);
				ps.setInt(1, startId);
				ps.setInt(2, howMany);
				return ps;
			}
		}, vehicleRowMapper);
	}

}
