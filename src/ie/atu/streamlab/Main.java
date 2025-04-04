package ie.atu.streamlab;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

        names.stream()
             .filter(name -> name.startsWith("A"))
             .forEach(name -> System.out.println(name));

        List<String> filteredNames = names.stream()
                                    .filter(name -> name.length() > 3) // Intermediate
                                    .sorted() // Intermediate
                                    .collect(Collectors.toList()); // Terminal

        // Now print the collected list
        filteredNames.forEach(name -> System.out.println(name)); // Terminal

        // Using lambda
        names.stream()
             .map(name -> name.toUpperCase())
             .forEach(name -> System.out.println(name));

        // Using method references
        names.stream()
             .map(String::toUpperCase)
             .forEach(System.out::println);

        // //////////////////////////////////////////////////////
        // DIY 1
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
           .filter(number -> number % 2 != 0)
           .forEach(number -> System.out.println(number));

        // DIY 2
        List<String> fruits = Arrays.asList("apple", "banana", "grape", "kiwi", "orange", "mango");
        List<String> fruitsModified = fruits.stream()
              .filter(fruit -> fruit.length() > 4)
              .sorted()
              .map(fruit -> fruit.toUpperCase())
              .collect(Collectors.toList());

        fruitsModified.forEach(fruit -> System.out.println(fruit));

        // DIY 3
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
        nums.stream()
            .map(NumberUtils::doubleNumber)
            .forEach(System.out::println);

        // DIY 4
        List<Integer> numbersReduce = Arrays.asList(2, 4, 6, 8, 10);
        int product = numbersReduce.stream()
                                   .reduce(1, (a, b) -> a * b);
        int min = numbersReduce.stream()
                               .reduce(Integer.MAX_VALUE, (a, b) -> Math.min(a, b));

        System.out.println("Product: " + product);
        System.out.println("Minimum value: " + min);

        // DIY 5
        String inputPath = "resources/input.txt";

        try (Stream<String> lines = Files.lines(Paths.get(inputPath))) {
            long lineCount = lines.filter(line -> line.contains("First"))
                                  .count();
        
            // Create a new stream for the second operation
            try (Stream<String> linesForAverage = Files.lines(Paths.get(inputPath))) {
                double averageLineLength = linesForAverage.mapToInt(String::length)
                                                          .average()
                                                          .orElse(0.0);
        
                // Print results
                System.out.println("Lines containing 'First': " + lineCount);
                System.out.println("Average line length: " + averageLineLength);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}