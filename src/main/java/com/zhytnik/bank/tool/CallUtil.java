package com.zhytnik.bank.tool;

import static java.lang.String.format;

public class CallUtil {

    private CallUtil() {
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
