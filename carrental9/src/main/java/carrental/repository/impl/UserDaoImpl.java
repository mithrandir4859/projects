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

import carrental.domain.User;
import carrental.domain.UserStatus;
import carrental.repository.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	// Constants
	// ----------------------------------------------------------------------------------------------------
	private static final String SQL_CREATE =
			"INSERT INTO Users (email, password, firstname, lastname, phone, userStatus) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String SQL_FIND_BY_ID =
			"SELECT email, firstname, lastname, phone, userStatus FROM Users WHERE userId = ?";
	private static final String SQL_FIND_BY_EMAIL = 
			"SELECT password, firstname, lastname, phone, userStatus, userId FROM Users WHERE email = ?";
	private static final String SQL_FIND_BY_EMAIL_AND_PASS =
			"SELECT firstname, lastname, phone, userStatus, userId FROM Users WHERE email = ? AND password = ?";
	private static final String SQL_UPDATE =
			"UPDATE Users SET email = ?, firstname = ?, lastname = ?, phone = ?, userStatus = ? WHERE userId = ?";
	private static final String SQL_UPDATE_STATUS =
			"UPDATE Users SET userStatus = ? WHERE userId = ?";
	private static final String SQL_CHANGE_PASSWORD =
			"UPDATE Users SET password = ? WHERE password = ? AND userId = ?";
	private static final String SQL_DELETE =
			"DELETE FROM Users WHERE userId = ?";
	private static final String SQL_COUNT =
			"SELECT COUNT(userId) FROM Users";
	private static final String SQL_LIST =
			"SELECT email, password, firstname, lastname, phone, userStatus, userId FROM Users";
	private static final String SQL_LIST_PARTIAL =
			SQL_LIST + " WHERE userId >= ? ORDER BY userId LIMIT ?";

	// Fields
	// ----------------------------------------------------------------------------------------------------
	private JdbcTemplate jdbcTemplate;
	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rSet, int arg1) throws SQLException {
			return new User(rSet.getString(1),
					rSet.getString(2),
					rSet.getString(3),
					rSet.getString(4),
					rSet.getString(5),
					UserStatus.values()[rSet.getInt(6) - 1],
					rSet.getInt(7));
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
	public void create(final User user) throws IllegalArgumentException {
		assertIsNull(user.getUserId());
		KeyHolder holder = new GeneratedKeyHolder();
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getFirstname());
				ps.setString(4, user.getLastname());
				ps.setString(5, user.getPhone());
				ps.setInt(6, user.getUserStatus().ordinal() + 1);
				return ps;
			}
		}, holder));
		user.setUserId(holder.getKey().intValue());
	}

	// Read methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public User find(final Integer id) {
		return jdbcTemplate.query(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_ID);
						ps.setInt(1, id);
						return ps;
					}
				}, new ResultSetExtractor<User>() {
					@Override
					public User extractData(ResultSet rSet) throws SQLException, DataAccessException {
						return rSet.next() ? new User(
								rSet.getString(1),
								null,
								rSet.getString(2),
								rSet.getString(3),
								rSet.getString(4),
								UserStatus.values()[rSet.getInt(5) - 1],
								id) : null;
					}
				});
	}

	@Override
	public User find(final String email, final String password) {
		return jdbcTemplate.query(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_EMAIL_AND_PASS);
						ps.setString(1, email);
						ps.setString(2, password);
						return ps;
					}
				}, new ResultSetExtractor<User>() {
					@Override
					public User extractData(ResultSet rSet) throws SQLException, DataAccessException {
						return rSet.next() ? new User(email,
								null,
								rSet.getString(1),
								rSet.getString(2),
								rSet.getString(3),
								UserStatus.values()[rSet.getInt(4) - 1],
								rSet.getInt(5)
						) : null;
					}
				});
	}

	// Update methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void update(final User user) throws IllegalArgumentException {
		final Integer userId = user.getUserId();
		assertNotNull(userId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
				ps.setString(1, user.getEmail());
				ps.setString(2, user.getFirstname());
				ps.setString(3, user.getLastname());
				ps.setString(4, user.getPhone());
				ps.setInt(5, user.getUserStatus().ordinal() + 1);
				ps.setInt(6, userId);
				return ps;
			}
		}));
	}

	@Override
	public void update(final Integer userId, final UserStatus newStatus) {
		assertNotNull(userId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STATUS);
				ps.setInt(1, newStatus.ordinal() + 1);
				ps.setInt(2, userId);
				return ps;
			}
		}));
	}

	@Override
	public void changePassword(final Integer userId, final String oldPassword, final String newPassword) throws IllegalArgumentException {
		assertNotNull(userId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement ps = arg0.prepareStatement(SQL_CHANGE_PASSWORD);
				ps.setString(1, newPassword);
				ps.setString(2, oldPassword);
				ps.setInt(3, userId);
				return ps;
			}
		}));
	}

	// Delete methods (from CRUDL)
	// ----------------------------------------------------------------------------------------------------

	@Override
	public void delete(User user) {
		delete(user.getUserId());
		user.setUserId(null);
	}

	@Override
	public void delete(final Integer userId) {
		assertNotNull(userId);
		assertOnlyOneRowAffected(jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement ps = arg0.prepareStatement(SQL_DELETE);
				ps.setInt(1, userId);
				return ps;
			}
		}));
	}

	@Override
	public int getUsersCounter() {
		return jdbcTemplate.query(SQL_COUNT, new ResultSetExtractor<Integer>() {
			@Override
			public Integer extractData(ResultSet rSet) throws SQLException, DataAccessException {
				rSet.next();
				return rSet.getInt(1);
			}
		});
	}

	@Override
	public List<User> list() {
		return jdbcTemplate.query(SQL_LIST, userRowMapper);
	}

	@Override
	public List<User> list(final Integer startId, final int howMany) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection arg0) throws SQLException {
				PreparedStatement ps = arg0.prepareStatement(SQL_LIST_PARTIAL);
				ps.setInt(1, startId);
				ps.setInt(2, howMany);
				return ps;
			}
		}, userRowMapper);
	}
	
	@Override
	public User find(final String email) {
		return jdbcTemplate.query(
				new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
						PreparedStatement ps = connection.prepareStatement(SQL_FIND_BY_EMAIL);
						ps.setString(1, email);
						return ps;
					}
				}, new ResultSetExtractor<User>() {
					@Override
					public User extractData(ResultSet rSet) throws SQLException, DataAccessException {
						return rSet.next() ? new User(
								email,
								rSet.getString(1),
								rSet.getString(2),
								rSet.getString(3),
								rSet.getString(4),
								UserStatus.values()[rSet.getInt(5) - 1],
								rSet.getInt(6)) : null;
					}
				});
	}

}
