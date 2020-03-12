package com.company;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("ДИСКЛЕЙМЕР \n " +
                "Данный интерпретататор математических выражений поддерживает следующие операции:\n" +
                "Сложение -> +; Вычитание -> - ; Умножение -> *; Деление -> /; Скобочки -> ()");
        System.out.println("Введите ваше выражение:");
        //System.out.println(Calc.Expression2ReversedPolishNotation("2+2*2"));
        // System.out.println(Calc.Expression2ReversedPolishNotation(new Scanner(System.in).nextLine()));
        // System.out.println(Calc.ReversedPolishNotation2Answer(Calc.Expression2ReversedPolishNotation(new Scanner(System.in).nextLine())));
        //System.out.println(Calc.ReversedPolishNotation2Answer(Calc.Expression2ReversedPolishNotation("10/(2-7+3)*4")));
        //System.out.println(new Calc().solutionOfExpression("2+2"));
         System.out.println(new Calc().solutionOfExpression(new Scanner(System.in).nextLine()));
    }
}
