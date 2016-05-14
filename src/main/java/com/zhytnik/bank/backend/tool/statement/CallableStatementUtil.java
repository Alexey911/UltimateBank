package com.zhytnik.bank.backend.tool.statement;

import com.zhytnik.bank.backend.exception.NotFoundException;
import com.zhytnik.bank.backend.types.IEntity;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.STRUCT;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.zhytnik.bank.backend.tool.ReflectionUtil.*;
import static com.zhytnik.bank.backend.tool.ScriptUtil.*;
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

    public static CallableStatement build(Connection connection, String script) {
        CallableStatement s;
        try {
            s = connection.prepareCall(script);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return s;
    }

    public static void execute(CallableStatement s) throws NotFoundException {
        try {
            s.executeUpdate();
        } catch (SQLException e) {
            if (e.getMessage().startsWith("ORA-01403: no data found")) {
                throw new NotFoundException();
            } else {
                throw new RuntimeException(e);
            }
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

    public static String loadString(CallableStatement s, int index) {
        String val;
        try {
            val = s.getString(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Integer loadInteger(CallableStatement s, int index) {
        int val;
        try {
            val = s.getInt(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static BigDecimal loadBigDecimal(CallableStatement s, int index) {
        BigDecimal val;
        try {
            val = s.getBigDecimal(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Date loadDate(CallableStatement s, int index) {
        Date val;
        try {
            val = s.getTimestamp(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Double loadDouble(CallableStatement s, int index) {
        Double val;
        try {
            val = s.getDouble(index);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static Boolean loadBoolean(CallableStatement s, int index) {
        Boolean val;
        try {
            val = (s.getInt(index) > 0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return val;
    }

    public static void registerParameter(CallableStatement s, int index, Class param) {
        try {
            if (isString(param)) {
                s.registerOutParameter(index, OracleTypes.VARCHAR);
            } else if (isInteger(param)) {
                s.registerOutParameter(index, OracleTypes.INTEGER);
            } else if (isDate(param)) {
                s.registerOutParameter(index, OracleTypes.DATE);
            } else if (isDouble(param)) {
                s.registerOutParameter(index, OracleTypes.DOUBLE);
            } else if (isBoolean(param)) {
                s.registerOutParameter(index, OracleTypes.INTEGER);
            } else if (isEntity(param)) {
                s.registerOutParameter(index, OracleTypes.INTEGER);
            } else {
                throw new RuntimeException("Unknown Type");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T extends IEntity> void registerCollection(CallableStatement s,
                                                              int index, Class<T> entityClass) {
        try {
            s.registerOutParameter(index, Types.ARRAY, getCollection(entityClass));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCollection(Class paramClass) {
        return paramClass.getSimpleName().toUpperCase() + ARRAY_OF_ALL;
    }

    public static void putString(CallableStatement s, int index, Object strVal) {
        try {
            s.setString(index, (String) strVal);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putInteger(CallableStatement s, int index, Object intVal) {
        try {
            if (intVal == null) {
                s.setNull(index, Types.INTEGER);
            } else {
                s.setInt(index, (Integer) intVal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putDate(CallableStatement s, int index, Object dateVal) {
        try {
            if (dateVal == null) {
                s.setNull(index, Types.TIMESTAMP);
            } else {
                final Timestamp t = new Timestamp(((Date) dateVal).getTime());
                s.setTimestamp(index, t);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putDouble(CallableStatement s, int index, Object doubleVal) {
        try {
            if (doubleVal == null) {
                s.setNull(index, Types.DOUBLE);
            } else {
                s.setDouble(index, (Double) doubleVal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void putBoolean(CallableStatement s, int index, Object booleanVal) {
        try {
            if (booleanVal == null) {
                s.setInt(index, 0);
            } else {
                s.setInt(index, ((Boolean) booleanVal) ? 1 : 0);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
