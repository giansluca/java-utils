package org.gmdev.functional.functionalinterface;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class _Predicate {

    public static void driver() {
        // Normal Java function
        System.out.println("Without predicate");
        System.out.println(isPhoneNumberValid("0734224683"));
        System.out.println(isPhoneNumberValid("073422468"));
        System.out.println(isPhoneNumberValid("0834224683"));

        // Predicate Functional interface
        System.out.println("With predicate");
        System.out.println(isPhoneNumberValidPredicate.test("0734224683"));
        System.out.println(isPhoneNumberValidPredicate.test("073422468"));
        System.out.println(isPhoneNumberValidPredicate.test("0834224683"));

        // Predicate chain 'AND'
        System.out.println(
                "Is phone number valis and contains 3 = " +
                        isPhoneNumberValidPredicate.and(containsNumber3Predicate).test("0754224680")
        );
        // Predicate chain 'OR'
        System.out.println(
                "Is phone number valis and contains 3 = " +
                        isPhoneNumberValidPredicate.or(containsNumber3Predicate).test("0754224680")
        );
        // BiPredicate
        System.out.println(
                "Double check: " + isValidConditionalCheckBipredicate.test("87342246838", true));
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.startsWith("07") && phoneNumber.length() == 10;
    }

    public static Predicate<String> isPhoneNumberValidPredicate = phoneNumber ->
            phoneNumber.startsWith("07") && phoneNumber.length() == 10;

    public static Predicate<String> containsNumber3Predicate = phoneNumber ->
            phoneNumber.contains("3");

    public static BiPredicate<String, Boolean> isValidConditionalCheckBipredicate = (phoneNumber, checkBoth) ->
            checkBoth ? phoneNumber.length() == 11 && phoneNumber.startsWith("07") : phoneNumber.length() == 11;

}
