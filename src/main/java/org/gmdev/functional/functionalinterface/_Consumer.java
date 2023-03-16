package org.gmdev.functional.functionalinterface;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class _Consumer {

    public static void driver() {
        var customer = new Customer("Gians", "3475047211");

        // Normal Java function
        greetCustomer(customer);

        // Consumer Functional interface
        greetCustomerConsumer.accept(customer);

        // Biconsumer Functional interface
        greetCustomerBiConsumer.accept(customer, false);

    }

    public static void greetCustomer(Customer customer) {
        System.out.println("Hello " + customer.customerName
                + ", phone number: " + customer.customerPhoneNumber);
    }

    public static Consumer<Customer> greetCustomerConsumer = customer ->
            System.out.println("Hello " + customer.customerName
                    + ", phone number: " + customer.customerPhoneNumber);

    public static BiConsumer<Customer, Boolean> greetCustomerBiConsumer = (customer, showPhoneNumber) ->
            System.out.println("Hello " + customer.customerName
                    + (showPhoneNumber ? ", phone number: " + customer.customerPhoneNumber : ", ************") );

    public static class Customer {
        private final String customerName;
        private final String customerPhoneNumber;

        public Customer(String customerName, String customerPhoneNumber) {
            this.customerName = customerName;
            this.customerPhoneNumber = customerPhoneNumber;
        }
    }

}
