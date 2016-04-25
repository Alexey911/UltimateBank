package com.zhytnik.bank.backend.tool;

import static java.lang.String.format;

public class ScriptUtil {

    public static final String LOAD_PROCEDURE_NAME = "LOAD_";
    public static final String SAVE_PROCEDURE_NAME = "SAVE_";
    public static final String UPDATE_PROCEDURE_NAME = "UPDATE_";
    public static final String COUNT_FUNCTION_NAME = "COUNT_";
    public static final String REMOVE_PROCEDURE_NAME = "REMOVE_";
    public static final String LOAD_ALL_FUNCTION_NAME = "LOAD_ALL_";
    public static final String ARRAY_OF_ALL = "_ARRAY";

    private ScriptUtil() {
    }

    public static String getProcedureCall(String procedure, int argsCount) {
        return format("BEGIN %s(%s); END;", procedure.toUpperCase(), getArgumentTemplate(argsCount));
    }

    public static String getFunctionCall(String function, int argsCount) {
        return format("BEGIN ? := %s(%s); END;", function.toUpperCase(), getArgumentTemplate(argsCount));
    }

    private static String getArgumentTemplate(int argCount) {
        String args = "";

        for (int i = 0; i < argCount; i++) args += " ?,";

        if (argCount > 0) {
            args = args.substring(0, args.length() - 1);
        }
        return args;
    }
}
