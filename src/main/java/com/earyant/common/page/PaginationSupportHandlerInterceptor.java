package com.earyant.common.page;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 分页插件
 *
 * @author duming
 * @date 2013-10-9
 */
@SuppressWarnings("unchecked")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationSupportHandlerInterceptor implements Interceptor {

    private final Log logger = LogFactory.getLog(getClass());

    /**
     * mapper.xml中需要拦截的ID(正则匹配)
     **/
    private static String PAGESQL_ID = "pageSqlId";

    private String pageSqlId;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
        BaseStatementHandler delegate = (BaseStatementHandler) ReflectionUtils.getFieldValue(routingStatementHandler, "delegate");
        MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");

        if (mappedStatement.getId().matches(pageSqlId)) {

            BoundSql boundSql = delegate.getBoundSql();
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                logger.error("参数对象尚未实例化！");
                throw new NullPointerException("参数对象尚未实例化！");
            }

            Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
            PaginationSupport pageBean = (PaginationSupport) parameterMap.get("pageBean");
            if (pageBean == null) {
                logger.error("分页对象不存在！");
                throw new NullPointerException("分页对象不存在！");
            }

            // 取得连接
            Connection connection = (Connection) invocation.getArgs()[0];
            String sql = boundSql.getSql();

            // 去除order by子句统计总记录数
            String newCountSql = "";
            newCountSql = delOrderbySQL(sql);
            int idx = newCountSql.toUpperCase().indexOf("FROM ");
            newCountSql = "select count(*) as cnt " + newCountSql.substring(idx);
            if (logger.isDebugEnabled()) {
                logger.debug("查询总记录SQL:" + newCountSql);
            }

            // 执行统计SQL
            BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), newCountSql, boundSql.getParameterMappings(), parameterObject);
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, newBoundSql);
            PreparedStatement ps = null;
            int count = 0;
            try {
                ps = connection.prepareStatement(newCountSql);
                parameterHandler.setParameters(ps);
                ResultSet rs = ps.executeQuery();
                count = (rs.next()) ? rs.getInt("cnt") : 0;
                rs.close();
            } catch (SQLException e) {
                throw new Exception("执行记录总数SQL时发生异常", e);
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    throw new Exception("关闭状态时发生异常", e);
                }
            }

            // 把记录总数放入对象pageBean中
            pageBean.setCount(count);

            //总页数赋值
            pageBean.setPageCount();

            String pageSql = getLimitString(sql, pageBean);
            // 将分页sql语句反射回BoundSql.
            ReflectionUtils.setFieldValue(boundSql, "sql", pageSql);

        }

        return invocation.proceed();
    }

    /**
     * 删除查询条件中的order by子句
     *
     * @param queryString 查询SQL语句
     * @return 删除查询语句中的order by子句后的查询语句
     */
    private String delOrderbySQL(String queryString) {
        StringBuffer temp = new StringBuffer();
        queryString = "(" + queryString + ")";

        String[] strArray = queryString.split("order by");
        for (int i = 1; i < strArray.length; i++) {
            strArray[i] = ")";
        }
        for (int i = 0; i < strArray.length; i++) {
            temp.append(strArray[i]);
        }
        String result = temp.toString();
        if (temp.length() >= 2) {
            result = result.substring(1, result.length() - 1);
        }
        return result;
    }

    private String getLimitString(String sql, PaginationSupport pageBean) {
        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
        pagingSelect.append("select t.* from(");
        pagingSelect.append(sql);
        int page = (pageBean.getCurPage() > 0) ? pageBean.getCurPage() : 1;
        int start = (page - 1) * pageBean.getPageSize();
        int end = pageBean.getPageSize();
        pagingSelect.append(" ) t LIMIT " + start + "," + end);
        return pagingSelect.toString();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        pageSqlId = properties.getProperty(PAGESQL_ID);
        if (pageSqlId == null || pageSqlId.length() < 1) {
            logger.error("pageSqlId property is not found!");
        }
    }

}