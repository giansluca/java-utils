package org.gmdev.functional.functionalinterface;

import java.util.function.BiFunction;
import java.util.function.Function;

public class _Function {

    public static void driver() {
        // Normal Java function
        int numberInc = incrementByOne(1);
        System.out.println(numberInc);

        // Function Functional interface

        // Function takes 1 argument and produce 1 result
        int increment = incrementByOneFunction.apply(1);
        System.out.println(increment);
        int multiply = multiplyByTenFunction.apply(increment);
        System.out.println(multiply);

        Function<Integer, Integer> incrementByOneAndMultiplyByTen
                = incrementByOneFunction.andThen(multiplyByTenFunction);
        int result_1 = incrementByOneAndMultiplyByTen.apply(4);
        System.out.println(result_1);

        // BiFunction takes 2 arguments and produce 1 result
        int result_2 = incrementAnMultiplyBiFunction.apply(1, 100);
        System.out.println(result_2);
    }

    public static int incrementByOne(int number) {
        return number + 1;
    }

    public static Function<Integer, Integer> incrementByOneFunction =
            number -> number + 1;

    public static Function<Integer, Integer> multiplyByTenFunction =
            number -> number * 10;

    public static BiFunction<Integer, Integer, Integer> incrementAnMultiplyBiFunction =
            (numberToIncrementByOne, numberToMultiply) -> (numberToIncrementByOne + 1) * numberToMultiply;
}
