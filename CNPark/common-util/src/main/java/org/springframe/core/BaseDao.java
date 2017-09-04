package org.springframe.core;



import org.springframe.util.Page;

import java.util.List;
import java.util.Map;



public abstract interface BaseDao<T> {

	/**
	 * 创建方法
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int create(String sql, Object[] obj);

	/**
	 * 创建返回唯一键
	 * @param obj
	 * @param sql
	 * @return
	 */
	public String create(Object[] obj, String sql);

	/**
	 * 查询返回对象<T>方法
	 * @param sql
	 * @param obj
	 * @return
	 */
	public T get(String sql, Object[] obj);

	/***
	 * 查询返回List<T>方法
	 * @param sql
	 * @param obj
	 * @return
	 */
	public List<T> getList(String sql, Object[] obj);
	
	
	public List<T> getList(String sql);

	/**
	 * 查询返回List<Map>方法
	 * @param sql
	 * @param obj
	 * @return
	 */
	public List<Map<String, Object>> getMap(String sql, Object[] obj);
	
	/**
	 * 查询返回List<Map<String,Object>>
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getMap(String sql);

	/**
	 * 查询返回记录数方法
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int count(String sql, Object[] obj);

	/**
	 * 修改方法
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int update(String sql, Object[] obj);

	/**
	 * 删除
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int delete(String sql, Object[] obj);

	/**
	 * 批量修改方法
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int[] batchUpdate(String sql, List<Object[]> obj);

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
	public Page<T> getList(String sql, Object[] obj, int pageNo, int pageSize);



	//public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException;
}
