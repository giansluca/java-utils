package org.gmdev.functional.callback;

import java.util.function.Consumer;

public class _Callback {

    public static void driver() {
        // Callback
        hello_1("Terence", null, value -> System.out.println("No last name for " + value));
        hello_2("Bob", null, () -> System.out.println("No last name"));
    }

    public static void hello_1(String firstName, String lastName, Consumer<String> callback) {
        System.out.println(firstName);
        if(lastName != null)
            System.out.println(lastName);
        else
            callback.accept(firstName);
    }

    public static void hello_2(String firstName, String lastName, Runnable callback) {
        System.out.println(firstName);
        if(lastName != null)
            System.out.println(lastName);
        else
            callback.run();
    }

}
