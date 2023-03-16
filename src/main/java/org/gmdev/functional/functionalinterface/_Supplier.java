package org.gmdev.functional.functionalinterface;

import java.util.List;
import java.util.function.Supplier;

public class _Supplier {

    public static void driver() {
        // Normal Java function
        System.out.println(getDBConnectionUrl());

        // Supplier Functional interface
        System.out.println(getDBConnectionUrlSupplier.get());
        System.out.println(getStringList.get());
    }

    public static String getDBConnectionUrl() {
        return "jdbc://localhost:5432/users";
    }

    public static Supplier<String> getDBConnectionUrlSupplier = () -> "jdbc://localhost:5432/users";

    public static Supplier<List<String>> getStringList = () ->
            List.of("list 1", "list 2", "list 3");

}
