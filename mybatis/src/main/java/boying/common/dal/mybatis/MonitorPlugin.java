package boying.common.dal.mybatis;


import boying.common.dal.SqlContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;


/**
 * 监控插件。打印sql，执行时间
 * <p/>
 * 参考：https://github.com/dianping/cat/blob/master/%E6%A1%86%E6%9E%B6%E5%9F%8B%E7%82%B9%E6%96%B9%E6%A1%88%E9%9B%86%E6%88%90/mybatis/CatMybatisPlugin.java
 */
@Intercepts({
        @Signature(method = "query", type = Executor.class, args = {
                MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class}),
        @Signature(method = "update", type = Executor.class, args = {MappedStatement.class, Object.class})
        /*
        @Signature(method = "rollback", type = Executor.class, args = {boolean.class}),
        @Signature(method = "commit", type = Executor.class, args = {boolean.class}),
        @Signature(method = "getTransaction", type = Executor.class, args = {})
        */
})
public class MonitorPlugin implements Interceptor {
    private static Logger logger = LoggerFactory.getLogger(MonitorPlugin.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final String methodName = invocation.getMethod().getName();
        switch (methodName) {
            case "update":
            case "query":
                return interceptUpdateQuery(invocation);
            case "rollback":
                // return interceptRollback(invocation);
                return invocation.proceed();
            case "commit":
                // return interceptCommit(invocation);
                return invocation.proceed();
            case "getTransaction":
                // return interceptGetTransaction(invocation);
                return invocation.proceed();
        }
        return invocation.proceed();

    }

    private Object interceptGetTransaction(Invocation invocation) throws Throwable {
        logger.info("Mybatis get transaction");
        return invocation.proceed();
    }

    private Object interceptCommit(Invocation invocation) throws Throwable {
        try {
            Object ret = invocation.proceed();
            logger.info("Mybatis commit success");
            return ret;
        } catch (Throwable t) {
            logger.error("Mybatis commit failed, ", t);
            throw t;
        }
    }

    private Object interceptRollback(Invocation invocation) throws Throwable {
        try {
            Object ret = invocation.proceed();
            logger.info("Mybatis rollback success");
            return ret;
        } catch (Throwable t) {
            logger.error("Mybatis rollback failed, ", t);
            throw t;
        }
    }

    private Object interceptUpdateQuery(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String[] strArr = mappedStatement.getId().split("\\.");
        String sqlId = strArr[strArr.length - 2] + "." + strArr[strArr.length - 1];
        String logHead = String.format("Mybatis[%s]", sqlId);

        //得到sql语句
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = showSql(configuration, boundSql);

        logger.info(String.format("%s sql is %s", logHead, sql));

        Object returnObj = null;
        try {
            long startTime = System.currentTimeMillis();
            SqlContext.getInstance().setSqlId(sqlId);
            returnObj = invocation.proceed();
            long finishTime = System.currentTimeMillis();
            logger.info(String.format("%s result is %s", logHead, returnObj.toString()));
            logger.info(String.format("%s cost %dms", logHead, finishTime - startTime));

        } catch (Exception e) {
            logger.error(String.format("%s sql execute failed", logHead), e);
            throw e;
        } finally {
            SqlContext.getInstance().remove();
        }

        return returnObj;
    }

    /**
     * 解析sql语句
     */
    public String showSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                }
            }
        }
        return sql;
    }

    /**
     * 参数解析
     */
    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }

        }
        return value;
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {
    }

}