package org.springframe.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.PreparedStatementSetter;

public class PreparedStatementSetters implements PreparedStatementSetter {

	private final String key;
	private final Object[] obj;

	public PreparedStatementSetters(String key, Object[] obj) {
		this.key = key;
		this.obj = obj;
	}

	@Override
	public void setValues(PreparedStatement ps) throws SQLException {
		ps.setObject(1, key);
		for (int i = 2; i <= obj.length; i++) {
			ps.setObject(i, obj[i - 1]);
		}
	}

}
