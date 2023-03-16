package org.gmdev.functional.callback;

import java.util.function.BiFunction;
import java.util.function.Function;

public class _Lambdas {

    // direct return
    Function<String, String> upperCaseName_1 = name -> name.toUpperCase();

    // with some logic
    Function<String, String> upperCaseName_2 = name -> {
        if(name.isBlank())
            throw new IllegalArgumentException();
        return name.toUpperCase();
    };

    // with two argument --> Bifunction
    BiFunction<String, Integer, String> upperCaseName_3 = (name, age) -> {
        if(name.isBlank())
            throw new IllegalArgumentException();
        System.out.println(age);
        return name.toUpperCase();
    };

    public static void driver() {
        // Lambdas
        var lambdas = new _Lambdas();
        System.out.println(lambdas.upperCaseName_1.apply("gians"));
        System.out.println(lambdas.upperCaseName_2.apply("terence"));
        System.out.println(lambdas.upperCaseName_3.apply("carl", 25));
    }



}
