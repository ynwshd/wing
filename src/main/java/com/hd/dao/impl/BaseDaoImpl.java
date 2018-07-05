package com.hd.dao.impl;

import com.fr.util.base.EscColumnToBean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.filter.impl.CachingWrapperFilter;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.fr.util.base.Reflections;
import com.fr.util.base.StringUtils;
import com.hd.dao.IBaseDao;
import com.hd.wing.entity.Vo;

@Repository
public class BaseDaoImpl<T> implements IBaseDao<T> {
    // @Autowired
    // @Resource(name = "sessionFactory")

    public SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // @Autowired
    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 实体类类型(由构造方法自动赋值)
     */
    private Class<?> entityClass;

    /**
     * 构造方法，根据实例类自动获取实体类类型
     */
    public BaseDaoImpl() {
        entityClass = Reflections.getClassGenricType(getClass());
    }

    /**
     * 获得当前事物的session
     *
     * @return org.hibernate.Session
     */
    public Session getCurrentSession() {
        // ctx = new ClassPathXmlApplicationContext("spring-hibernate.xml");
        // ctx = new ClassPathXmlApplicationContext(new String[] {
        // "classpath:spring.xml", "classpath:spring-hibernate.xml",
        // "WEB-INF/remote-servlet.xml" });
        // sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
        return this.sessionFactory.getCurrentSession();
        // return sessionFactory.openSession();
    }

    @Override
    public Serializable save(T o) {
        if (o != null) {
            return getCurrentSession().save(o);
        }
        return null;
    }

    @Override
    public T getById(Class<T> c, Serializable id) {
        return (T) getCurrentSession().get(c, id);
    }

    @Override
    public T getByHql(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T getByHql(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public void delete(T o) {
        if (o != null) {
            getCurrentSession().delete(o);
        }
    }

    @Override
    public void update(T o) {
        if (o != null) {
            getCurrentSession().update(o);
        }
    }

    @Override
    public void saveOrUpdate(T o) {

        if (o != null) {
            getCurrentSession().saveOrUpdate(o);
        }

    }

    @Override
    public List<T> find(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return q.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.list();
    }

    @Override
    public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public List<T> find(String hql, int page, int rows) {
        Query q = getCurrentSession().createQuery(hql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public Long count(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return (Long) q.uniqueResult();
    }

    @Override
    public Long count(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (Long) q.uniqueResult();
    }

    @Override
    public int executeHql(String hql) {
        Query q = getCurrentSession().createQuery(hql);
        return q.executeUpdate();
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        Query q = getCurrentSession().createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public List<Map> findBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public List<Map> findBySql(String sql, int page, int rows) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
    }

    @Override
    public List<Map> findBySql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    @Override
    public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
                .list();
    }

    @Override
    public int executeSql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return q.executeUpdate();
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.executeUpdate();
    }

    @Override
    public BigDecimal countBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        return (BigDecimal) q.uniqueResult();
    }

    @Override
    public BigDecimal countBySql(String sql, Map<String, Object> params) {
        // SQLQuery q = getCurrentSession().createSQLQuery(sql);
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        // q.setResultTransformer(new EscColumnToBean(pojoClass));
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return (BigDecimal) q.uniqueResult();
    }

    @Override
    public T getBySql(String sql, Class<?> pojoClass) {
        // SQLQuery q = getCurrentSession().createSQLQuery(sql);
        // List<T> l = q.list();
        Query q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        // 返回查询结果
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T getBySql(String sql, Map<String, Object> params, Class<?> pojoClass) {
        // SQLQuery q = getCurrentSession().createSQLQuery(sql);
        Query q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public List<T> findBySql(String sql, Class<?> pojoClass) {
        Query q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        // 返回查询结果
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<T> findBySql(String sql, Map<String, Object> params, Class<?> pojoClass) {
        Query q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l;
        }
        return null;
    }

    @Override
    public List<T> findByProc(String procedureName, Map<String, Object> params, Class<?> pojoClass) {
        // procedureName("CALL GetStocks(:stockCode)")
        // Query q = getCurrentSession().createSQLQuery(
        // "CALL GetStocks(:stockCode)").addEntity(pojoClass);
        // q.setParameter("stockCode", "7277");
        Query q = getCurrentSession().createSQLQuery(procedureName);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l;
        }
        return null;
    }

    /**
     * 通过SQL执行无返回结果的存储过程(仅限于存储过程)
     *
     * @param queryString
     * @param params
     * @throws Exception
     */
    @Override
    public void executeVoidProcedureSql(final String queryString, final Object[] params) throws Exception {
        // Session session = getCurrentSession();
        getCurrentSession().doWork(new Work() {
            public void execute(java.sql.Connection conn) throws SQLException {
                ResultSet rs = null;
                CallableStatement call = conn.prepareCall("{" + queryString + "}");
                if (null != params) {
                    for (int i = 0; params != null && i < params.length; i++) {
                        call.setObject(i + 1, params[i]);
                    }
                }
                rs = call.executeQuery();
                call.close();
                rs.close();

            }
        });

    }

    /**
     * 通过存储过程查询(单结果集)
     *
     * @param sql 查询sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return
     */
    @Override
    public List<Map<String, Object>> find_procedure(final String sql, final Object[] params, final int outIndex, final int type) {
        final List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try {
            // Session session = getCurrentSession();
            getCurrentSession().doWork(new Work() {
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs = null;
                    ResultSet rs = null;
                    cs = conn.prepareCall(sql);
                    for (int i = 1; params != null && i <= params.length; i++) {
                        cs.setObject(i, params[i - 1]);// 设置参数

                    }
                    cs.registerOutParameter(outIndex, type);
                    // rs = cs.executeQuery();
                    // cs.execute();
                    cs.executeQuery();
                    rs = (ResultSet) cs.getObject(outIndex);
                    ResultSetMetaData metaData = rs.getMetaData();
                    int colCount = metaData.getColumnCount();
                    while (rs.next()) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        for (int i = 1; i <= colCount; i++) {
                            String colName = metaData.getColumnName(i);
                            map.put(colName, rs.getObject(colName));
                        }
                        result.add(map);
                    }
                    close(cs, rs);
                }
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void close(CallableStatement cs, ResultSet rs) {
        try {
            if (cs != null) {
                cs.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过存储过程查询(多结果集)
     *
     * @param sql 查询sql
     * @param params 参数
     * @param columnNum 返回的列数
     * @return
     */
    @Override
    public List<List<Map<String, Object>>> find_procedure_multi(final String sql, final Object[] params) {
        final List<List<Map<String, Object>>> result = new ArrayList<List<Map<String, Object>>>();
        try {
            // conn =
            // SessionFactoryUtils.getDataSource(sessionFactory).getConnection();
            // Session session = getCurrentSession();
            getCurrentSession().doWork(new Work() {
                public void execute(Connection conn) throws SQLException {
                    CallableStatement cs = null;
                    ResultSet rs = null;
                    cs = conn.prepareCall(sql);
                    for (int i = 1; params != null && i <= params.length; i++) {
                        cs.setObject(i, params[i - 1]);
                    }
                    boolean hadResults = cs.execute();
                    ResultSetMetaData metaData = null;
                    while (hadResults) {// 遍历结果集
                        List<Map<String, Object>> rsList = new ArrayList<Map<String, Object>>();// 用于装该结果集的内容
                        rs = cs.getResultSet();// 获取当前结果集
                        metaData = rs.getMetaData();
                        int colCount = metaData.getColumnCount();// 获取当前结果集的列数
                        while (rs.next()) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            for (int i = 1; i <= colCount; i++) {
                                String colName = metaData.getColumnName(i);
                                map.put(colName, rs.getObject(colName));
                            }
                            rsList.add(map);
                        }
                        result.add(rsList);
                        close(null, rs);// 遍历完一个结果集，将其关闭
                        hadResults = cs.getMoreResults();// 移到下一个结果集
                    }
                    close(cs, rs);
                }
            });
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findBySql(String sql, Map<String, Object> params, int page, int rows, Class<?> pojoClass) {
        Query q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        q.setResultTransformer(new EscColumnToBean(pojoClass));
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    @Override
    public int executeProcedureSql(final String queryString, final Object[] params, final int outIndex, final int type,
            final int outIndex2, final int type2) throws Exception {
        final int[] r = {0};// 返回值
        getCurrentSession().doWork(new Work() {
            public void execute(java.sql.Connection conn) throws SQLException {
                // ResultSet rs = null;
                CallableStatement cs = conn.prepareCall("{" + queryString + "}");
                if (null != params) {
                    for (int i = 0; params != null && i < params.length; i++) {
                        cs.setObject(i + 1, params[i]);
                    }
                }
                cs.registerOutParameter(outIndex, type);
                cs.registerOutParameter(outIndex2, type2);
                // rs = cs.executeQuery();
                cs.executeQuery();
                r[0] = cs.getInt(outIndex);
                cs.close();
            }
        });
        return r[0];
    }

    @Override
    public String executeProcedureSql(final String queryString, final Object[] params, final int outIndex3, final int type3,
            final int outIndex, final int type, final int outIndex2, final int type2) throws Exception {
        final Vo v = new Vo();
        getCurrentSession().doWork(new Work() {
            public void execute(java.sql.Connection conn) throws SQLException {
                // ResultSet rs = null;
                CallableStatement cs = conn.prepareCall("{" + queryString + "}");
                if (null != params) {
                    for (int i = 0; params != null && i < params.length; i++) {
                        cs.setObject(i + 1, params[i]);
                    }
                }
                cs.registerOutParameter(outIndex3, type3);
                cs.registerOutParameter(outIndex, type);
                cs.registerOutParameter(outIndex2, type2);
                // rs = cs.executeQuery();
                cs.executeQuery();
                v.setStrData(cs.getString(outIndex3) + "," + cs.getInt(outIndex) + "," + cs.getString(outIndex2));
                cs.close();
            }
        });
        return v.getStrData();
    }

    /**
     * * 执行带返回参数的存储过程
     */
    @Override
    public String executeProcedureSql(final String queryString, final Object[] params, final int[] outIndex, final int[] type)
            throws Exception {
        final Vo v = new Vo();
        getCurrentSession().doWork(new Work() {
            public void execute(java.sql.Connection conn) throws SQLException {
                // ResultSet rs = null;
                CallableStatement cs = conn.prepareCall("{" + queryString + "}");
                if (null != params) {
                    for (int i = 0; params != null && i < params.length; i++) {
                        cs.setObject(i + 1, params[i]);
                    }
                }
                if (outIndex != null) {
                    for (int i = 0; outIndex != null && i < outIndex.length; i++) {
                        cs.registerOutParameter(outIndex[i], type[i]);
                    }
                }
                // rs = cs.executeQuery();
                cs.executeQuery();
                String str = "";
                if (outIndex != null) {
                    for (int i = 0; outIndex != null && i < outIndex.length; i++) {
                        if (type[i] == 4) {
                            // 整数
                            str += cs.getInt(outIndex[i]) + ";";
                        }
                        if (type[i] == 12) {
                            // 字符串
                            str += cs.getString(outIndex[i]) + ";";
                        }
                    }
                }
                String fstr = str.substring(0, str.length() - 1);
                v.setStrData(fstr);
                cs.close();
            }
        });
        return v.getStrData();
    }

    @Override
    public void saveOrUpdateAutoCommit(T o) {
        Assert.notNull(o, "保存对象不能为空");
        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            session.saveOrUpdate(o);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveAutoCommit(T o) {

        Assert.notNull(o, "保存对象不能为空");
        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            session.save(o);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteAutoCommit(T o) {
        Assert.notNull(o, "删除对象不能为空");
        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            session.delete(o);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void addBatchAutoCommit(List<T> oList) {
        Assert.notNull(oList, "保存对象列表不能为空");

        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            for (T o : oList) {
                session.save(o);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public String[] callProcedureAutoCommit(final String queryString, final Object[] params, final int[] outIndex,
            final int[] type, final int appCodeIdx) throws Exception {
        final Vo v = new Vo();

        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        final String resultArr[] = new String[outIndex.length];
        try {

            session.doWork(new Work() {
                @Override
                public void execute(java.sql.Connection conn) throws SQLException {
                    try ( // ResultSet rs = null;
                            CallableStatement cs = conn.prepareCall("{" + queryString + "}")) {
                        if (null != params) {
                            for (int i = 0; i < params.length; i++) {
                                cs.setObject(i + 1, params[i]);
                            }
                        }
                        if (outIndex != null) {
                            for (int i = 0; i < outIndex.length; i++) {
                                cs.registerOutParameter(outIndex[i], type[i]);
                            }
                        }

                        cs.executeQuery();

                        if (outIndex != null) {
                            for (int i = 0; i < outIndex.length; i++) {

                                if (cs.getObject(outIndex[i]) == null) {
                                    resultArr[i] = "";
                                }

                                if (cs.getObject(outIndex[i]) instanceof BigDecimal) {
                                    resultArr[i] = ((BigDecimal) cs.getObject(outIndex[i])) + "";
                                }

                                if (cs.getObject(outIndex[i]) instanceof String) {
                                    resultArr[i] = ((String) cs.getObject(outIndex[i]));
                                }
                            }
                        }
                    }
                }
            });

            int appCode = Integer.parseInt(resultArr[appCodeIdx]);

            resultArr[appCodeIdx] = Integer.toString(appCode);

            if (appCode == 1) {

                tx.commit();
            } else {

                tx.rollback();
            }
        } catch (NumberFormatException | HibernateException e) {
            e.printStackTrace();
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }

        return resultArr;
    }

    @Override
    public void removeBatchAutoCommit(List<T> oList) {
        Assert.notNull(oList, "删除对象列表不能为空");

        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            for (T o : oList) {
                session.delete(o);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public T getBySql(String sql) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public T getBySql(String sql, Map<String, Object> params) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        List<T> l = q.list();
        if (l != null && l.size() > 0) {
            return l.get(0);
        }
        return null;
    }

    @Override
    public void saveOrUpdateBatchAutoCommit(List<T> oList) {
        Assert.notNull(oList, "保存对象列表不能为空");

        Session session = sessionFactory.openSession(); // 获取Session
        Transaction tx = session.beginTransaction(); // 开启事物
        try {
            for (T o : oList) {
                session.saveOrUpdate(o);
            }
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public Integer getSeq(String sql) {
        Integer maxId = (Integer) (getCurrentSession().createSQLQuery(sql).addScalar("nextvalue", StandardBasicTypes.INTEGER))
                .uniqueResult();
        return maxId;
    }

    @Override
    public void executeBatchSql(final String[] sql) throws Exception {
        Session session = sessionFactory.openSession(); // 获取Session
        try {
            session.doWork(new Work() {
                public void execute(java.sql.Connection conn) throws SQLException {
                    Statement stmt = null;
                    try {
                        stmt = conn.createStatement();
                        if (sql != null) {
                            for (int i = 0; i < sql.length; i++) {
                                stmt.addBatch((String) sql[i]);
                            }
                            stmt.executeBatch();
                        }

                    } catch (Exception e) {
                        throw e;
                    } finally {
                        conn.close();
                        stmt.close();
                    }
                }
            });
        } catch (Exception ex) {
            throw ex;
        } finally {
            session.close();
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findBySqlIncludeBlobClob(String sql, Map<String, Object> params, int page, int rows, Class<?> pojoClass) {
        SQLQuery q = getCurrentSession().createSQLQuery(sql);
        // 设置结果集转换器，这是本文重点所在
        // q.setResultTransformer(new EscColumnToBean(pojoClass));
        setResultTransformer(q, pojoClass);
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                q.setParameter(key, params.get(key));
            }
        }
        return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
    }

    /**
     * 设置查询结果类型
     *
     * @param query
     * @param resultClass
     */
    private void setResultTransformer(SQLQuery query, Class<?> resultClass) {
        if (resultClass != null) {
            if (resultClass == Map.class) {
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            } else if (resultClass == List.class) {
                query.setResultTransformer(Transformers.TO_LIST);
            } else {
                query.addEntity(resultClass);
            }
        }
    }

    // -------------- Hibernate search start--------------
    /**
     * 获取全文Session
     */
    @Override
    public FullTextSession getFullTextSession() {
        return Search.getFullTextSession(getCurrentSession());
    }

    /**
     * 建立索引
     */
    @Override
    public void createIndex() {
        try {
            getFullTextSession().createIndexer(entityClass).startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void purgeIndex() {
        try {
            getFullTextSession().purgeAll(entityClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

;

/**
 * 全文检索
 *
 * @param page 分页对象
 * @param query 关键字查询对象
 * @param queryFilter 查询过滤对象
 * @param sort 排序对象
 * @return 分页对象
 */
//	@SuppressWarnings("unchecked")
//    @Override
//    public Page<T> search(Page<T> page, BooleanQuery query, BooleanQuery queryFilter, Sort sort) {
//
//        // 按关键字查询
//        FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, entityClass);
//        // 过滤无效的内容
//        if (queryFilter != null) {
//            fullTextQuery.setFilter(new CachingWrapperFilter(new QueryWrapperFilter(queryFilter)));
//        }
//        // 设置排序
//        if (sort != null) {
//            fullTextQuery.setSort(sort);
//        }
//
//        // 定义分页
//        page.setCount(fullTextQuery.getResultSize());
//        fullTextQuery.setFirstResult(page.getFirstResult());
//        fullTextQuery.setMaxResults(page.getMaxResults());
//
//        // 先从持久化上下文中查找对象，如果没有再从二级缓存中查找
//        fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SECOND_LEVEL_CACHE, DatabaseRetrievalMethod.QUERY);
//
//        // 返回结果
//        page.setList(fullTextQuery.list());
//
//        return page;
//    }
/**
 * 获取全文查询对象
 */
//    @Override
//    public BooleanQuery getFullTextQuery(BooleanClause... booleanClauses) {
//        BooleanQuery booleanQuery = new BooleanQuery();
//        for (BooleanClause booleanClause : booleanClauses) {
//            booleanQuery.add(booleanClause);
//        }
//        return booleanQuery;
//    }
//    /**
//     * 获取全文查询对象
//     *
//     * @param q 查询关键字
//     * @param fields 查询字段
//     * @return 全文查询对象
//     */
//    @Override
//    public BooleanQuery getFullTextQuery(String q, String... fields) {
//        Analyzer analyzer = new IKAnalyzer();
//        BooleanQuery query = new BooleanQuery();
//        try {
//            if (StringUtils.isNotBlank(q)) {
//                for (String field : fields) {
//                    QueryParser parser = new QueryParser(Version.LUCENE_36, field, analyzer);
//                    query.add(parser.parse(q), Occur.SHOULD);
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return query;
//    }
/**
 * 设置关键字高亮
 *
 * @param query 查询对象
 * @param list 设置高亮的内容列表
 * @param subLength 截取长度
 * @param fields 字段名
 */
//    @Override
//    public List<T> keywordsHighlight(BooleanQuery query, List<T> list, int subLength, String... fields) {
//        Analyzer analyzer = new IKAnalyzer();
//        Formatter formatter = new SimpleHTMLFormatter("<span class=\"highlight\">", "</span>");
//        Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query));
//        highlighter.setTextFragmenter(new SimpleFragmenter(subLength));
//
//        for (T entity : list) {
//            try {
//                for (String field : fields) {
//                    String text = StringUtils.replaceHtml((String) Reflections.invokeGetter(entity, field));
//                    // 设置高亮字段
//                    String description = highlighter.getBestFragment(analyzer, field, text);
//                    if (description != null) {
//                        Reflections.invokeSetter(entity, fields[0], description);
//                        break;
//                    }
//                    Reflections.invokeSetter(entity, fields[0], StringUtils.abbr(text, subLength * 2));
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (InvalidTokenOffsetsException e) {
//                e.printStackTrace();
//            }
//        }
//        return list;
//    }
// -------------- Hibernate search end--------------
}
