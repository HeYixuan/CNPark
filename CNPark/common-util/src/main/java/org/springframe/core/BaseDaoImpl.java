package org.springframe.core;

import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframe.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.*;


public abstract class BaseDaoImpl<T> implements BaseDao<T> {


	private Class<T> clazz;

	public Class<T> getClazz() {
		if (null == clazz) {
			// 获取泛型的Class对象
			clazz = ((Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clazz;
	}

	/*private Class<?> clazz;

	public Class<?> getClazz() {
		if (null == clazz) {
			// 获取泛型的Class对象
			clazz = ((Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
		}
		return clazz;
	}*/
	//@Qualifier("primaryJdbcTemplate")

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public int create(String sql, Object[] obj) {
		return jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pstmt) throws SQLException {
				for (int i = 1; i <= obj.length; i++) {
					pstmt.setObject(i, obj[i - 1]);
				}
			}
		});
	}
	
	/**
	 * 创建返回唯一键
	 * @param obj
	 * @param sql
	 * @return
	 */
	public String create(Object[] obj, String sql){
		final String key = UUID.randomUUID().toString();
		int count = jdbcTemplate.update(sql, new PreparedStatementSetters(key, obj));
		if (count > 0) {
			return key;
		}
		return null;
	}
	
	public T get(String sql, Object[] obj) {
		return this.queryForObject(sql, obj, BeanPropertyRowMapper.newInstance(getClazz()));
		//return jdbcTemplate.queryForObject(sql, obj, BeanPropertyRowMapper.newInstance(getClazz()));
	}
	
	public List<T> getList(String sql, Object[] obj) {
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(getClazz()), obj);
	}
	
	public List<T> getList(String sql) {
		return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(getClazz()));
	}
	
	
	/**
	 * 查询返回List<Map>方法
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getMap(String sql, Object[] obj){
		return jdbcTemplate.queryForList(sql, obj);
	}
	
	/**
	 * 查询返回List<Map<String,Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getMap(String sql){
		return jdbcTemplate.queryForList(sql);
	}

	public int count(String sql, Object[] obj) {
		return jdbcTemplate.queryForObject(sql, obj, Integer.class);
	}

	public int update(String sql, Object[] obj) {
		return jdbcTemplate.update(sql, obj);
	}

	public int delete(String sql, Object[] obj) {
		return jdbcTemplate.update(sql, obj);
	}

	public int[] batchUpdate(String sql, List<Object[]> obj) {
		return jdbcTemplate.batchUpdate(sql, obj);
	}

	/**
	 * 返回实体分页列表
	 * 
	 *            实体
	 * @param sql
	 *            查询语句
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            每页数量
	 * @param obj
	 *            参数
	 * @return 返回实体分页列表
	 */
	public Page<T> getList(String sql, Object[] obj, int pageNo, int pageSize){
		final Page<T> page = new Page<T>(pageNo, pageSize);
		page.setTotalCount(getList(sql, obj).size());
		sql += " LIMIT " + page.getFirstIndex() + ", " + pageSize;
		List<T> list = getList(sql, obj);
		page.setData(list);
		return page;
	}


	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {
		List<T> results = jdbcTemplate.query(sql, args, new RowMapperResultSetExtractor<T>(rowMapper, 1));
		return this.requiredSingleResult(results);
	}


	public static <T> T requiredSingleResult(Collection<T> results) throws IncorrectResultSizeDataAccessException {
		int size = (results != null ? results.size() : 0);
		if (size == 0) {
			return null;
		}
		if (results.size() > 1) {
			throw new IncorrectResultSizeDataAccessException(1, size);
		}
		return results.iterator().next();
	}
}
