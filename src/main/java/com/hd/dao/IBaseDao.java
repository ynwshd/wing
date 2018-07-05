package com.hd.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Sort;
import org.hibernate.search.FullTextSession;



/**
 * 基础数据库操作类
 *
 * 其他DAO继承此类获取常用的数据库操作方法，基本上你能用到的方法这里都有了，不需要自己建立DAO了。
 *
 *
 * @param <T>
 *            模型
 */
public interface IBaseDao<T> {

	/**
	 * 保存一个对象
	 *
	 * @param o
	 *            对象
	 * @return 对象的ID
	 */
	public Serializable save(T o);

	/**
	 * 删除一个对象
	 *
	 * @param o
	 *            对象
	 */
	public void delete(T o);

	/**
	 * 更新一个对象
	 *
	 * @param o
	 *            对象
	 */
	public void update(T o);

	/**
	 * 保存或更新一个对象
	 *
	 * @param o
	 *            对象
	 */
	public void saveOrUpdate(T o);

	/**
	 * 通过主键获得对象
	 *
	 * @param c
	 *            类名.class
	 * @param id
	 *            主键
	 * @return 对象
	 */
	public T getById(Class<T> c, Serializable id);

	/**
	 * 通过HQL语句获取一个对象
	 *
	 * @param hql
	 *            HQL语句
	 * @return 对象
	 */
	public T getByHql(String hql);

	/**
	 * 通过HQL语句获取一个对象
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getByHql(String hql, Map<String, Object> params);

	/**
	 * 获得对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @return List
	 */
	public List<T> find(String hql);

	/**
	 * 获得对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params);

	/**
	 * 获得分页后的对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, int page, int rows);

	/**
	 * 获得分页后的对象列表
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return List
	 */
	public List<T> find(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 统计数目
	 *
	 * @param hql
	 *            HQL语句(select count(*) from T)
	 * @return long
	 */
	public Long count(String hql);

	/**
	 * 统计数目
	 *
	 * @param hql
	 *            HQL语句(select count(*) from T where xx = :xx)
	 * @param params
	 *            参数
	 * @return long
	 */
	public Long count(String hql, Map<String, Object> params);

	/**
	 * 执行一条HQL语句
	 *
	 * @param hql
	 *            HQL语句
	 * @return 响应结果数目
	 */
	public int executeHql(String hql);

	/**
	 * 执行一条HQL语句
	 *
	 * @param hql
	 *            HQL语句
	 * @param params
	 *            参数
	 * @return 响应结果数目
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 * 获得结果集
	 *
	 * @param sql
	 *            SQL语句
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql);

	/**
	 * 获得结果集
	 *
	 * @param sql
	 *            SQL语句
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, int page, int rows);

	/**
	 * 获得结果集
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params);

	/**
	 * 获得结果集
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @param page
	 *            要显示第几页
	 * @param rows
	 *            每页显示多少条
	 * @return 结果集
	 */
	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows);

	/**
	 * 执行SQL语句
	 *
	 * @param sql
	 *            SQL语句
	 * @return 响应行数
	 */
	public int executeSql(String sql);

	/**
	 * 执行SQL语句
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 响应行数
	 */
	public int executeSql(String sql, Map<String, Object> params);

	/**
	 * 统计
	 *
	 * @param sql
	 *            SQL语句
	 * @return 数目
	 */
	public BigDecimal countBySql(String sql);

	/**
	 * 统计
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 数目
	 */
	public BigDecimal countBySql(String sql, Map<String, Object> params);

	/**
	 * 通过SQL语句获取一个对象
	 *
	 * @param sql
	 *            SQL语句
	 * @return 对象
	 */
	public T getBySql(String sql);

	/**
	 * 通过SQL语句获取一个对象
	 *
	 * @param sql
	 *            SQL语句
	 * @return 对象
	 */
	public T getBySql(String sql, Class<?> pojoClass);

	/**
	 * 通过SQL语句获取一个对象
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getBySql(String sql, Map<String, Object> params);

	/**
	 * 通过SQL语句获取一个对象
	 *
	 * @param sql
	 *            SQL语句
	 * @param params
	 *            参数
	 * @return 对象
	 */
	public T getBySql(String sql, Map<String, Object> params, Class<?> pojoClass);

	/**
	 * 通过sql获取list对象映射pojo
	 *
	 * @param sql
	 * @param pojoClass
	 * @return
	 */
	public List<T> findBySql(String sql, Class<?> pojoClass);

	/**
	 * 通过sql获取list对象映射pojo
	 *
	 * @param sql
	 * @param params
	 * @param pojoClass
	 * @return
	 */
	public List<T> findBySql(String sql, Map<String, Object> params, Class<?> pojoClass);

	/**
	 * 调用存储过程返回结果集List
	 *
	 * @param procedureName
	 *            调用方式CALL GetStocks(:param1,:param2...)
	 * @param params
	 * @param pojoClass
	 * @return
	 */
	public List<T> findByProc(String procedureName, Map<String, Object> params, Class<?> pojoClass);

	/**
	 * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
	 *
	 * @param queryString
	 * @param params
	 * @throws Exception
	 */
	public void executeVoidProcedureSql(final String queryString, final Object[] params) throws Exception;

	/**
	 * 通过存储过程查询(单结果集)
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            参数
	 * @param columnNum
	 *            返回的列数
	 * @return
	 */
	public List<Map<String, Object>> find_procedure(final String sql, final Object[] params, final int outIndex, final int type);

	/**
	 * 通过存储过程查询(多结果集)
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            参数
	 * @param columnNum
	 *            返回的列数
	 * @return
	 */
	public List<List<Map<String, Object>>> find_procedure_multi(final String sql, final Object[] params);

	/**
	 * sql语句查询出列表
	 *
	 * @param sql
	 * @param page
	 * @param rows
	 * @param pojoClass
	 * @return
	 */
	public List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<?> pojoClass);

	/**
	 * 执行带两个输出参数的存储过程
	 *
	 * @param queryString
	 * @param params
	 * @param outIndex
	 * @param type
	 * @return
	 */
	public int executeProcedureSql(final String queryString, final Object[] params, final int outIndex, final int type,
			final int outIndex2, final int type2) throws Exception;

	/**
	 * 执行带三个输出参数的存储过程
	 *
	 * @param queryString
	 * @param params
	 * @param outstr
	 *            返回前台
	 * @param type3
	 * @param outIndex
	 * @param type
	 * @param outIndex2
	 * @param type2
	 * @return
	 * @throws Exception
	 */
	public String executeProcedureSql(final String queryString, final Object[] params, final int outIndex3, final int type3,
			final int outIndex, final int type, final int outIndex2, final int type2) throws Exception;

	/**
	 * *
	 *
	 * @param queryString
	 * @param params
	 * @param m
	 * @return
	 * @throws Exception
	 */
	public String executeProcedureSql(final String queryString, final Object[] params, final int[] outIndex, final int[] type)
			throws Exception;

	/**
	 * 保存或更新一个对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void saveOrUpdateAutoCommit(T o);

	/**
	 * 保存一个对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void saveAutoCommit(T o);

	/**
	 * 保存一批对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void addBatchAutoCommit(List<T> oList);

	/**
	 * 保存或更新一批对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void saveOrUpdateBatchAutoCommit(List<T> oList);

	/**
	 * 删除一批对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void removeBatchAutoCommit(List<T> oList);

	/**
	 * 删除一个对象,带事务提交
	 *
	 * @param o
	 *            对象
	 */
	public void deleteAutoCommit(T o);

	/**
	 * * 调用数据库存储过程 带事务提交
	 *
	 * @param queryString
	 * @param params
	 * @param outIndex
	 *            输出对象索引位置
	 * @param type
	 *            输出对象类型
	 * @return
	 * @throws Exception
	 */
	public String[] callProcedureAutoCommit(final String queryString, final Object[] params, final int[] outIndex,
			final int[] type, final int appCodeIdx) throws Exception;

	/**
	 * 获取序列
	 * 
	 * @return
	 */
	public Integer getSeq(String sql);

	/**
	 * 在批量执行sql
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public void executeBatchSql(final String[] sql) throws Exception;

	/**
	 * 查询sql返回包括blob、clob字段的映射
	 * 
	 * @param sql
	 * @param params
	 * @param page
	 * @param rows
	 * @param pojoClass
	 * @return
	 */
	public List<T> findBySqlIncludeBlobClob(String sql, Map<String, Object> params, int page, int rows, Class<?> pojoClass);

	/**
	 * 获取全文Session
	 */
	public FullTextSession getFullTextSession();

	/**
	 * 建立索引
	 */
	public void createIndex();

	/**
	 * 删除索引
	 */
	public void purgeIndex();

	/**
	 * 全文检索
	 * 
	 * @param page
	 *            分页对象
	 * @param query
	 *            关键字查询对象
	 * @param queryFilter
	 *            查询过滤对象
	 * @param sort
	 *            排序对象
	 * @return 分页对象
	 */
//	public Page<T> search(Page<T> page, BooleanQuery query, BooleanQuery queryFilter, Sort sort);

	/**
	 * 获取全文查询对象
	 */
	//public BooleanQuery getFullTextQuery(BooleanClause... booleanClauses);

	/**
	 * 获取全文查询对象
	 * 
	 * @param q
	 *            查询关键字
	 * @param fields
	 *            查询字段
	 * @return 全文查询对象
	 */
	//public BooleanQuery getFullTextQuery(String q, String... fields);

	/**
	 * 设置关键字高亮
	 * 
	 * @param query
	 *            查询对象
	 * @param list
	 *            设置高亮的内容列表
	 * @param subLength
	 *            截取长度
	 * @param fields
	 *            字段名
	 */
	//public List<T> keywordsHighlight(BooleanQuery query, List<T> list, int subLength, String... fields);
}
