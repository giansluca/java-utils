package org.gmdev.functional.combinatorpattern;

import java.time.LocalDate;

public class _Combinator {

    public static void driver() {
        // Norma java functions
        Customer customer = new Customer(
                "Terence",
                "user@email.com",
                "+00393475047211",
                LocalDate.of(1998, 9, 30)
        );
        //System.out.println(new CustomerValidatorService().isValid(customer));

        // Combinator pattern
        CustomerRegistrationValidator.ValidationResult result = CustomerRegistrationValidator.isEmailValid()
                .and(CustomerRegistrationValidator.isPhoneNumberValid())
                .and(CustomerRegistrationValidator.isAdult())
                .apply(customer);

        System.out.println(result);

        if(result != CustomerRegistrationValidator.ValidationResult.SUCCESS)
            throw new IllegalStateException(result.name());

    }

}
