package com.company;

import java.sql.SQLOutput;
import java.util.Hashtable;
import java.util.Stack;

public class Calc {
    // Решение подготовленного выражения в ОПН
    public double solutionOfExpression (String expression) {
        String preparedExpression = getAdoptedExpression(expression);
        String reversedPolishNotation = Expression2ReversedPolishNotation(preparedExpression);

        return ReversedPolishNotation2Answer(reversedPolishNotation);
    }

    // Учитываем возможную запись -2 или -(-2) и тп.
    public String getAdoptedExpression(String expression) {
        String adoptedExpression = new String();

        for (int token = 0; token < expression.length(); token++) {
            char symbol = expression.charAt(token);

            if (symbol == '-') {
                //Чекаем первый символ, явл ли он -
                if (token == 0) {
                    adoptedExpression += '0';
                } else if (expression.charAt(token - 1) == '(') {
                    adoptedExpression+='0';
                }

                adoptedExpression += symbol;
            }
        }

        return adoptedExpression;
    }

    // Выражение в обратную польскую нотацию
    public static String Expression2ReversedPolishNotation(String expression) {
        // Текущее МАТ.выражения
        String currentExpression = "";
        Stack<Character> stack= new Stack<>();

        int priority;
        for (int i = 0; i < expression.length(); i++) {
            priority = getPriority(expression.charAt(i));

            if (priority == 0) {
                // Число
                currentExpression += expression.charAt(i);
            } else if (priority == 1) {
                // Открывающая скобка "("
                stack.push(expression.charAt(i));
            } else if (priority > 1) {
                // Ставим разделитель для разичения 22 2 2
                currentExpression += " ";

                // Проверяем наличие элементов в Стеке
                while (!stack.empty()) {
                    // Сравниваем верхний приоритет с текущим
                    if (getPriority(stack.peek()) >= priority) {
                        currentExpression += stack.pop();
                    } else {
                        break;
                    }
                }

                // Кладем в стек, текущий символ:
                stack.push(expression.charAt(i));
            }

            // Закрывающая скобка ")"
            if (priority == -1) {
                currentExpression += " ";

                // А вот тут я обосрался и поставил "-1" и все смерть
                while (getPriority(stack.peek()) != 1) {
                    currentExpression += stack.pop();
                }

                stack.pop();
            }
        }

        while (!stack.empty()) {
            currentExpression += stack.pop();
        }

        return currentExpression;
    }

    // Обратная польская нотация в ответ, как ни странно :/
    public static double ReversedPolishNotation2Answer(String rpn) {
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i < rpn.length(); i++) {
            // Сравниваем символ с пробелом и ничего не делаем:3
            if (rpn.charAt(i) == ' ') {
                continue;
            }

            if (getPriority(rpn.charAt(i)) == 0) {
                String operand = new String();

                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    // Переход к следующемуу символу
                    operand += rpn.charAt(i++);

                    if (i == rpn.length()) {
                        break;
                    }
                }

                stack.push(Double.parseDouble(operand));
            }

            if (getPriority(rpn.charAt(i)) > 1) {
                // Забираем из стека два последних числа
                double a = stack.pop();
                double b = stack.pop();

                switch (rpn.charAt(i)) {
                    case '+':
                        stack.push(b + a);
                        break;
                    case '-':
                        stack.push(b - a);
                        break;
                    case '*':
                        stack.push(b * a);
                        break;
                    case '/':
                        stack.push(b / a);
                        break;
                }
            }
        }

        return stack.pop();
    }

    private static int getPriority(char token) {
        //if (token =="ln" || token == 'e' || token == "sqrt" )
        //if (token == cos)
        //if (token == sin)
        //if (token == tg)
        //if (token == ctg)

        // TODO: Make type of key string and save as property
        Hashtable<char, int> tokens = new Hashtable<char, int>();

        tokens.put('*', 3);
        tokens.put('/', 3);
        tokens.put('+', 2);
        tokens.put('-', 2);
        tokens.put('(', 1);
        tokens.put(')', -1);

        if (tokens.containsKey(token)) {
            return tokens.get(token);
        } else {
            return 0;
        }
    }
}