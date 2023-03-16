package org.gmdev.functional.optionals;

import java.util.Optional;

public class _Optional {

    public static void driver() {
        // Optionals

        // or else get
        Object value_1 = Optional.empty()
                .orElseGet(() -> "Default value");
        System.out.println(value_1);

        // or else throw
        Object value_2 = Optional.of("HI")
                .orElseThrow(() -> new IllegalStateException("Exception!"));
        System.out.println(value_2);

        // if present
        // some logic here
        Optional.of("YES")
                .ifPresent(System.out::println);
        // if present --> same of above
        Optional.of("YES").ifPresent(System.out::println);

        // if present or else
        Optional.empty()
                .ifPresentOrElse(
                        email -> System.out.println("sending email to: " + email),
                        () -> System.out.println("Can't send email")
                );




    }
}
