package org.gmdev.functional.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class _Stream {

    public static void driver() {
        List<Person> people = getPeople();

        // Filter
        Predicate<Person> predicate = person -> person.getGender().equals(Gender.FEMALE);
        List<Person> females_2 = people.stream()
                .filter(predicate)
                .toList();
        //females_2.forEach(System.out::println);

        // Sort
        List<Person> sorted = people.stream()
                .sorted(Comparator.comparing(Person::getAge).reversed())
                .toList();
        //sorted.forEach(System.out::println);

        // All match
        boolean allMatch = people.stream()
                .allMatch(person -> person.getAge() > 35);
        //System.out.println(allMatch);

        // Any match
        boolean anyMatch = people.stream()
                .anyMatch(person -> person.getAge() > 35);
        //System.out.println(anyMatch);

        // None match
        boolean noneMatch = people.stream()
                .noneMatch(person -> person.getName().equals("Gians"));
        //System.out.println(noneMatch);

        // Max
        people.stream()
                .max(Comparator.comparing(Person::getAge));
                //.ifPresent(System.out::println);

        // Min
        people.stream()
                .min(Comparator.comparing(Person::getAge));
                //.ifPresent(System.out::println);

        // Group
        Map<Gender, List<Person>> groupByGender = people.stream()
                .collect(Collectors.groupingBy(Person::getGender));
        groupByGender.forEach( (gender, peopleGender) -> {
            //System.out.println(gender);
            //peopleGender.forEach(System.out::println);
        });

        // Map -> generate a new stream with transformation, Map take a Function
        people.stream()
                .map(person -> person.getAge() + "a");
                //.forEach(name -> System.out.println(name));

        people.stream()
                .map(Person::getGender)
                .collect(Collectors.toSet())
                .forEach(System.out::println);
    }

    private static List<Person> getPeople() {
        return List.of(
            new Person("Gians", 71, Gender.MALE),
            new Person("Bob", 50, Gender.MALE),
            new Person("Maria", 70, Gender.FEMALE),
            new Person("Terence", 32, Gender.MALE),
            new Person("Babette", 48, Gender.FEMALE)
        );
    }

}
