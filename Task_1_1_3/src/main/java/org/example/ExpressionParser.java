package org.example;

/**
 * Разбивает выражения, записанные в виде строки.
 */
public class ExpressionParser {
    private final String input;
    private int position;

    /**
     * Конструктор для парсера заданной строки.
     *
     * @param input заданная строка
     */
    public ExpressionParser(String input) {
        this.input = input;
        this.position = 0;
    }

    /**
     * Пропускает пробелы.
     */
    private void skipSpaces() {
        while (this.position < this.input.length()
                && Character.isWhitespace(this.input.charAt(position))) {
            this.position++;
        }
    }

    /**
     * Проверяет наличие ожидаемого символа в текущей позиции.
     *
     * @param expected ожидаемый символ
     * @return true, если символ найден и позиция сдвинута; false - иначе
     */
    private boolean match(char expected) {
        if (this.position < this.input.length()
                && this.input.charAt(this.position) == expected) {
            this.position++;

            return true;
        }

        return false;
    }

    /**
     * Разбирает множитель: число, переменную или выражение в скобках.
     *
     * @return разобранное выражение
     */
    private Expression parseFactor() {
        skipSpaces();

        if (position >= input.length()) {
            throw new IllegalArgumentException("Выход за длину строки");
        }

        if (match('(')) {
            Expression expression = parseExpression();

            skipSpaces();

            if (!match(')')) {
                throw new IllegalArgumentException("Ожидалась закрывающая скобка");
            }

            return expression;
        }

        if (Character.isDigit(input.charAt(position))) {
            int start = position;

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {
                position++;
            }

            int number = Integer.parseInt(input.substring(start, position));

            return new Number(number);
        } else if (Character.isLetter(input.charAt(position))) {
            int start = position;

            while (position < input.length()
                    && Character.isLetterOrDigit(input.charAt(position))) {
                position++;
            }

            String variable = input.substring(start, position);

            return new Variable(variable);
        }

        throw new IllegalArgumentException();
    }

    /**
     * Разбирает выражение с умножением и делением.
     *
     * @return разобранное выражение с учетом приоритета операций
     */
    private Expression parseTerm() {
        Expression result = parseFactor();

        while (true) {
            skipSpaces();

            if (match('*')) {
                result = new Mul(result, parseFactor());
            } else if (match('/')) {
                result = new Div(result, parseFactor());
            } else {
                return result;
            }
        }
    }

    /**
     * Разбирает выражение со сложением и вычитанием.
     *
     * @return разобранное выражение
     */
    private Expression parseExpression() {
        Expression result = parseTerm();

        while (true) {
            skipSpaces();

            if (match('+')) {
                result = new Add(result, parseTerm());
            } else if (match('-')) {
                result = new Sub(result, parseTerm());
            } else {
                return result;
            }
        }
    }

    /**
     * Разбирает всю входную строку.
     *
     * @return разобранное выражение
     */
    public Expression parse() {
        Expression result = parseExpression();

        skipSpaces();

        if (position != input.length()) {
            throw new IllegalArgumentException("Лишние символы в выражении");
        }

        return result;
    }
}
