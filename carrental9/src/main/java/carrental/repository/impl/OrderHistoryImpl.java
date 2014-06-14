package carrental.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import carrental.domain.OrderHistory;
import carrental.domain.OrderStatus;
import carrental.repository.OrderHistoryDao;

public class OrderHistoryImpl implements OrderHistoryDao {
	private JdbcTemplate jdbcTemplate;
	private static final String SQL_FIND = "SELECT changeTime, orderId, orderStatus, reason, payment FROM OrderHistory WHERE orderId = ?";
	private static final String SQL_FIND_LATEST = "SELECT * FROM OrderHistory WHERE changeTime IN (SELECT MAX(changeTime) FROM OrderHistory WHERE orderId = ?) AND orderId = ?";
	private static final String SQL_CREATE = "INSERT INTO OrderHistory (orderId, orderStatus, reason, payment) VALUES (?, ?, ?, ?)";

	public OrderHistoryImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<OrderHistory> find(final Integer orderId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND);
				preparedStatement.setInt(1, orderId);
				return preparedStatement;
			}
		}, new RowMapper<OrderHistory>() {
			@Override
			public OrderHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
				OrderHistory orderHistory = new OrderHistory();
				orderHistory.setChangeTime(new LocalDate(rs.getDate(1)));
				orderHistory.setOrderId(rs.getInt(2));
				orderHistory.setOrderStatus(OrderStatus.values()[rs.getInt(3) - 1]);
				orderHistory.setReason(rs.getString(4));
				orderHistory.setPayment(rs.getInt(5));
				return orderHistory;
			}
		});
	}

	@Override
	public void create(final OrderHistory orderHistory) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_CREATE);
				preparedStatement.setInt(1, orderHistory.getOrderId());
				preparedStatement.setInt(2, orderHistory.getOrderStatus().ordinal() + 1);
				String reason = orderHistory.getReason();
				preparedStatement.setString(3, reason == null ? "" : reason);
				preparedStatement.setInt(4, orderHistory.getPayment());
				return preparedStatement;
			}
		});
	}

	@Override
	public OrderHistory findLatest(final Integer orderId) {
		return jdbcTemplate.query(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND_LATEST);
				preparedStatement.setInt(1, orderId);
				preparedStatement.setInt(2, orderId);
				return preparedStatement;
			}
		}, new ResultSetExtractor<OrderHistory>() {
			@Override
			public OrderHistory extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next())
					return null;
				Date changeTime = rs.getDate("changeTime");
				if (changeTime == null)
					return null;
				OrderHistory orderHistory = new OrderHistory();
				orderHistory.setReason(rs.getString("reason"));
				orderHistory.setChangeTime(new LocalDate(changeTime));
				orderHistory.setOrderId(orderId);
				orderHistory.setPayment(rs.getInt("payment"));
				orderHistory.setOrderStatus(OrderStatus.values()[rs.getInt("orderStatus") - 1]);
				return orderHistory;
			}
		});
	}

}
