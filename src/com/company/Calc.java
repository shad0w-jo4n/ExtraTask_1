package com.company;

import java.sql.SQLOutput;
import java.util.Stack;

public class Calc {

    //Решение подготовленного выражения в ОПН
    public double solutionOfExpression (String expression){
        String preparedExpression = getAdoptedExpression(expression);
        String reversedPolishNotation = Expression2ReversedPolishNotation(preparedExpression);
        return ReversedPolishNotation2Answer(reversedPolishNotation);
    }
    //Учитываем возможную запись -2 или -(-2) и тп.
    public String getAdoptedExpression(String expression){
        String adoptedExpression = new String();
        for (int token = 0; token< expression.length();token++){
            char symbol = expression.charAt(token);
            if (symbol == '-')
                if (token == 0) //Чекаем первый символ, явл ли он -
                    adoptedExpression+='0';
                else if (expression.charAt(token-1)=='('){
                    adoptedExpression+='0';
                }
                adoptedExpression+=symbol;
        }
        return adoptedExpression;
    }
    //Выражение в обратную польскую нотацию
    public static String Expression2ReversedPolishNotation(String expression){
        String currentExpression = "";        //Текущее МАТ.выражения
        Stack<Character> stack= new Stack<>();

        int priority;
        for (int i=0;i<expression.length();i++){
            priority = getPriority(expression.charAt(i));
            if (priority==0)
                currentExpression +=expression.charAt(i); //Число
            if (priority==1)
                stack.push(expression.charAt(i));         //Открывающая скобка "("
            if (priority > 1){
                currentExpression+=" "; //Ставим разделитель для разичения 22 2 2
                //Проверяем наличие элементов в Стеке
                while (!stack.empty()){
                    if (getPriority(stack.peek())>=priority)//Сравниваем верхний приоритет с текущим
                        currentExpression+=stack.pop();
                    else break;
                }
                stack.push(expression.charAt(i)); //Кладем в стек, текущий символ:
            }
            if(priority==-1){                                           //Закрывающая скобка ")"
                currentExpression+=" ";
                while (getPriority(stack.peek())!=1) //А вот тут я обосрался и поставил "-1" и все смерть
                    currentExpression+=stack.pop();
                stack.pop();
            }
        }
        while (!stack.empty())
            currentExpression+=stack.pop();
        return currentExpression;
    }
    //Обратная польская нотация в ответ, как ни странно :/
    public static double ReversedPolishNotation2Answer(String rpn){
        String operand;
        Stack<Double> stack = new Stack<>();

        for (int i=0;i<rpn.length();i++) {
            if (rpn.charAt(i)==' ')
                continue;   //Сравниваем символ с пробелом и ничего не делаем:3
            if (getPriority(rpn.charAt(i))==0){
                operand = new String();
                while (rpn.charAt(i)!=' ' && getPriority(rpn.charAt(i))==0 ){
                    operand+=rpn.charAt(i++); //Переход к следующемуу символу
                    if(i == rpn.length())
                        break;
                }
            stack.push(Double.parseDouble(operand));
            }
            if(getPriority(rpn.charAt(i))>1){
                double a = stack.pop(),b=stack.pop(); // Забираем из стека два последних числа
                    if(rpn.charAt(i)=='+')stack.push(b+a);
                    if(rpn.charAt(i)=='-')stack.push(b-a);
                    if(rpn.charAt(i)=='*')stack.push(b*a);
                    if(rpn.charAt(i)=='/')stack.push(b/a);
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
        if (token == '*' || token == '/')
            return 3;
        else if (token == '+' || token == '-')
            return 2;
        else if (token == '(')
            return 1;
        else if (token == ')')
            return -1;
        else return 0;      //Приоритет чисел
    }
}
