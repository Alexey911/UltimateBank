package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.domain.IEntity;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.script.CallUtil.getFunctionCall;
import static com.zhytnik.bank.backend.tool.script.CallUtil.getProcedureCall;
import static com.zhytnik.bank.backend.tool.script.ScriptUtil.ARRAY_OF_ALL;
import static com.zhytnik.bank.backend.tool.statement.AggregateUtil.fill;

public class CallableStatementUtil {

    private CallableStatementUtil() {
    }

    public static <T extends IEntity> CallableStatement build(Connection connection, Class<T> entityClass,
                                                              String callName, boolean function, int argsCount) {
        final String entity = entityClass.getSimpleName();
        final String script = function ? getFunctionCall(callName + entity, argsCount) :
                getProcedureCall(callName + entity, argsCount);

        CallableStatement s;
        try {
            s = connection.prepareCall(script);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return s;
    }

    public static void execute(CallableStatement s) {
        try {
            s.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void close(CallableStatement s) {
        try {
            s.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public static <T extends IEntity> Set<T> extractEntities(CallableStatement s, Class<T> entityClass) {
        final Set<T> entities = new HashSet<>();
        try {
            ARRAY desc = (ARRAY) s.getArray(1);
            for (Object struct : getStructures(desc)) {
                final T entity = instantiate(entityClass);
                fill(entity, (STRUCT) struct);
                entities.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return entities;
    }

    private static Object[] getStructures(ARRAY desc) throws SQLException {
        return (Object[]) desc.getArray();
    }

    public static String loadString(CallableStatement stmt, int index) {
        String val;
        try {
            val = stmt.getString(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Integer loadInteger(CallableStatement stmt, int index) {
        int val;
        try {
            val = stmt.getInt(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static BigDecimal loadBigDecimal(CallableStatement stmt, int index) {
        BigDecimal val;
        try {
            val = stmt.getBigDecimal(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Date loadDate(CallableStatement stmt, int index) {
        Date val;
        try {
            val = stmt.getDate(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Double loadDecimal(CallableStatement stmt, int index) {
        Double val;
        try {
            val = stmt.getDouble(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static void registerParameter(CallableStatement stmt, int index, Class paramClass) {
        try {
            if (isString(paramClass)) {
                stmt.registerOutParameter(index, OracleTypes.VARCHAR);
            } else if (isInteger(paramClass)) {
                stmt.registerOutParameter(index, OracleTypes.INTEGER);
            } else if (isDate(paramClass)) {
                stmt.registerOutParameter(index, OracleTypes.DATE);
            } else if (isDouble(paramClass)) {
                stmt.registerOutParameter(index, OracleTypes.DOUBLE);
            } else if (isEntity(paramClass)) {
                stmt.registerOutParameter(index, OracleTypes.INTEGER);
            } else {
                throw new RuntimeException("Unknown Type");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends IEntity> void registerCollection(CallableStatement stmt,
                                                              int index, Class<T> entityClass) {
        try {
            stmt.registerOutParameter(index, Types.ARRAY, getCollection(entityClass));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCollection(Class paramClass) {
        return paramClass.getSimpleName().toUpperCase() + ARRAY_OF_ALL;
    }

    public static void putString(CallableStatement stmt, int index, Object strVal) {
        try {
            stmt.setString(index, (String) strVal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putInteger(CallableStatement stmt, int index, Object intVal) {
        try {
            if (intVal == null) {
                stmt.setNull(index, Types.INTEGER);
            } else {
                stmt.setObject(index, (Integer) intVal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putDate(CallableStatement stmt, int index, Object dateVal) {
        final java.sql.Date sqlDate = new java.sql.Date(((Date) dateVal).getTime());
        try {
            stmt.setDate(index, sqlDate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putDecimal(CallableStatement stmt, int index, Object decimalVal) {
        try {
            stmt.setDouble(index, (Double) decimalVal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
