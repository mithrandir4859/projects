package carrental.repository.impl;

import static carrental.repository.jdbc.DaoUtils.assertIsNull;
import static carrental.repository.jdbc.DaoUtils.assertNotNull;
import static carrental.repository.jdbc.DaoUtils.assertOnlyOneRowAffected;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import carrental.domain.Order;
import carrental.domain.OrderStatus;
import carrental.repository.OrderDao;

@Repository
public class OrderDaoImpl implements OrderDao {

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final String SQL_CREATE =
			"INSERT INTO Orders (userId, vehicleId, startTime, endTime, orderStatus) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_ID =
			"SELECT userId, vehicleId, startTime, endTime, orderStatus FROM Orders WHERE orderId = ?";
	private static final String SQL_FIND_BY_USERID =
			"SELECT userId, vehicleId, startTime, endTime, orderStatus, orderId FROM Orders WHERE userId = ?";
	private static final String SQL_FIND_BY_VEHICLEID =
			"SELECT userId, vehicleId, startTime, endTime, orderStatus, orderId FROM Orders WHERE vehicleId = ?";
	private static final String SQL_UPDATE =
			"UPDATE Orders SET userId = ?, vehicleId = ?, startTime = ?, endTime = ?, orderStatus = ? WHERE orderId = ?";
	private static final String SQL_UPDATE_STATUS =
			"UPDATE Orders SET orderStatus = ? WHERE orderId = ?";
	private static final String SQL_DELETE =
			"DELETE FROM Orders WHERE orderId = ?";
	private static final String SQL_COUNT =
			"SELECT COUNT(userId) FROM Users";
	private static final String SQL_LIST =
			"SELECT userId, vehicleId, startTime, endTime, orderStatus, orderId FROM Orders";
	private static final String SQL_LIST_PARTIAL =
			SQL_LIST + " WHERE orderId >= ? ORDER BY orderId LIMIT ?";

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private JdbcTemplate jdbcTemplate;
	private RowMapper<Order> orderRowMapper = new RowMapper<Order>() {
		@Override
		public Order mapRow(ResultSet rSet, int arg1) throws SQLException {
			return new Order(
					rSet.getInt(1),
					rSet.getInt(2),
					rSet.getLong(3),
					rSet.getLong(4),
					OrderStatus.values()[rSet.getInt(5) - 1],
					rSet.getInt(6));
		}
	};

	// Setter
	// ----------------------------------------------------------------------------------------------------
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// Create methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void create(final Order order) {
		assertIsNull(order.getOrderId());
		KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, order.getUserId());
				ps.setInt(2, order.getVehicleId());
				ps.setLong(3, order.getStartTimeMillis());
				ps.setLong(4, order.getEndTimeMillis());
				ps.setInt(5, order.getOrderStatus().ordinal() + 1);
				return ps;
			}
		}, generatedKeyHolder));
		order.setOrderId(generatedKeyHolder.getKey().intValue());
	}

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public Order find(final Integer orderId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_ID);
				ps.setInt(1, orderId);
				return ps;
			}
		}, new ResultSetExtractor<Order>() {
			@Override
			public Order extractData(ResultSet rSet) throws SQLException, DataAccessException {
				return rSet.next() ? new Order(
						rSet.getInt(1),
						rSet.getInt(2),
						rSet.getLong(3),
						rSet.getLong(4),
						OrderStatus.values()[rSet.getInt(5) - 1],
						orderId) : null;
			}
		});
	}

	@Override
	public List<Order> findByUser(final Integer userId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_USERID);
				ps.setInt(1, userId);
				return ps;
			}
		}, orderRowMapper);
	}

	@Override
	public List<Order> findByVehicle(final Integer vehicleId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_FIND_BY_VEHICLEID);
				ps.setInt(1, vehicleId);
				return ps;
			}
		}, orderRowMapper);
	}

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void update(final Order order) throws IllegalArgumentException {
		final Integer orderId = order.getOrderId();
		assertNotNull(orderId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_UPDATE);
				ps.setInt(1, order.getUserId());
				ps.setInt(2, order.getVehicleId());
				ps.setLong(3, order.getStartTimeMillis());
				ps.setLong(4, order.getEndTimeMillis());
				ps.setInt(5, order.getOrderStatus().ordinal() + 1);
				ps.setInt(6, orderId);
				return ps;
			}
		}));

	}

	@Override
	public void update(final Integer orderId, final OrderStatus orderStatus) {
		assertNotNull(orderId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_UPDATE_STATUS);
				ps.setInt(1, orderStatus.ordinal() + 1);
				ps.setInt(2, orderId);
				return ps;
			}
		}));

	}

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void delete(Order order) throws IllegalArgumentException {
		delete(order.getOrderId());
		order.setOrderId(null);
	}
	
	@Override
	public void delete(final Integer orderId) throws IllegalArgumentException {
		assertNotNull(orderId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_DELETE);
				ps.setInt(1, orderId);
				return ps;
			}
		}));
		
	}

	// Listing methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public int getOrderCounter() {
		return jdbcTemplate.query(SQL_COUNT, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rSet) throws SQLException, DataAccessException {
				rSet.next();
				return rSet.getInt(1);
			}
		});
	}

	@Override
	public List<Order> list() {
		return jdbcTemplate.query(SQL_LIST, orderRowMapper);
	}

	@Override
	public List<Order> list(final Integer startId, final int howMany) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection c) throws SQLException {
				PreparedStatement ps = c.prepareStatement(SQL_LIST_PARTIAL);
				ps.setInt(1, startId);
				ps.setInt(2, howMany);
				return ps;
			}
		}, orderRowMapper);
	}

}
