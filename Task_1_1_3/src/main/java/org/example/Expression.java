package org.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Expression {

    @Override
    public abstract String toString();

    public void print() {
        System.out.println(this.toString());
    }

    public abstract int eval(Map<String, Integer> vars);

    public int eval(String varsStr) {
        return eval(parseVars(varsStr));
    }

    private Map<String, Integer> parseVars(String varsStr) {
        if (varsStr == null || varsStr.isBlank()) {
            return Collections.emptyMap();
        }

        Map<String, Integer> vars = new HashMap<>();
        String[] pairs = varsStr.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String varName = keyValue[0].trim();
                int value = Integer.parseInt(keyValue[1].trim());
                vars.put(varName, value);
            }
        }

        return vars;
    }
}
