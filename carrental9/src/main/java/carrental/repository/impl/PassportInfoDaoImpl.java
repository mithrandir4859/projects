package carrental.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;

import carrental.domain.PassportInfo;
import carrental.repository.PassportInfoDao;

public class PassportInfoDaoImpl implements PassportInfoDao {

	private static final String SQL_FIND = "SELECT * FROM PassportInfo WHERE userId = ?";
	private static final String SQL_CREATE = "INSERT INTO PassportInfo (userId, series, number, additionalInfo, issuedMillis) VALUES (?, ?, ?, ?, ?)";
	private static final String SQL_UPDATE = "UPDATE PassportInfo SET series=?, number=?, additionalInfo=?, issuedMillis=? WHERE userId = ?";
	private static final String SQL_DELETE = "DELETE FROM PassportInfo WHERE userId = ?";

	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public PassportInfo find(final Integer userId) {
		return jdbcTemplate.query(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_FIND);
				preparedStatement.setInt(1, userId);
				return preparedStatement;
			}
		}, new ResultSetExtractor<PassportInfo>() {
			@Override
			public PassportInfo extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (!rs.next())
					return null;
				return new PassportInfo(rs.getInt("userId"),
						rs.getString("series"),
						rs.getInt("number"),
						rs.getString("additionalInfo"),
						rs.getLong("issuedMillis"));
			}
		});
	}

	@Override
	public void create(final PassportInfo passportInfo) {
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_CREATE);
				int i = 1;
				preparedStatement.setInt(i++, passportInfo.getUserId());
				preparedStatement.setString(i++, passportInfo.getSeries());
				preparedStatement.setInt(i++, passportInfo.getNumber());
				preparedStatement.setString(i++, passportInfo.getAdditionalInfo());
				preparedStatement.setLong(i++, passportInfo.getIssuedMillis());
				return preparedStatement;
			}
		});

	}

	@Override
	public void update(final PassportInfo passportInfo) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
				int i = 1;
				preparedStatement.setString(i++, passportInfo.getSeries());
				preparedStatement.setInt(i++, passportInfo.getNumber());
				preparedStatement.setString(i++, passportInfo.getAdditionalInfo());
				preparedStatement.setLong(i++, passportInfo.getIssuedMillis());
				preparedStatement.setInt(i++, passportInfo.getUserId());
				return preparedStatement;
			}
		});

	}

	@Override
	public void delete(final Integer userId) {
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE);
				preparedStatement.setInt(1, userId);
				return preparedStatement;
			}
		});
	}

}
