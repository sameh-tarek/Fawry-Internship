package org.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

public class StreamsExample {

    public static void main(final String[] args) {

        List<Author> authors = Library.getAuthors();
        
        banner("Authors information");
        // SOLVED With functional interfaces declared
        Consumer<Author> authorPrintConsumer = new Consumer<Author>() {
            @Override
            public void accept(Author author) {
                System.out.println(author);
            }
        };
        authors
            .stream()
            .forEach(authorPrintConsumer);

        banner("Authors information - lambda");
        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .forEach(System.out::println);

        banner("Active authors");
        // SOLVED With functional interfaces declared
        Predicate<Author> activeAuthorsPredicate = new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.active;
            }
        };
        authors
            .stream()
            .filter(activeAuthorsPredicate)
            .forEach(authorPrintConsumer);

        banner("Active authors - lambda");
        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .filter(author -> author.active)
            .forEach(System.out::println);

        banner("Active books for all authors");
        // SOLVED With functional interfaces declared
        Function<Author, List<Book>> authorBooksListFunction = new Function<Author, List<Book>>() {
            @Override
            public List<Book> apply(Author author) {
                return author.books;
            }
        };
        Function<List<Book>, Stream<Book>> streamAllBooksFunction = new Function<List<Book>, Stream<Book>>() {
            @Override
            public Stream<Book> apply(List<Book> books) {
                return books.stream();
            }
        };
        Consumer<Book> bookPrintConsumer = new Consumer<Book>() {
            @Override
            public void accept(Book book) {
                System.out.println(book);
            }
        };
        Predicate<Book> publishedBooksPredicate = new Predicate<Book>() {
            @Override
            public boolean test(Book book) {
                return book.published;
            }
        };
        authors
            .stream()
            .filter(activeAuthorsPredicate)
            .map(authorBooksListFunction)
            .flatMap(streamAllBooksFunction)
            .filter(publishedBooksPredicate)
            .forEach(bookPrintConsumer);

        banner("Active books for all authors - lambda");
        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .filter(author -> author.active)
            .flatMap(author -> author.books.stream())
            .filter(book -> book.published)
            .forEach(System.out::println);

        banner("Average price for all books in the library");
        // SOLVED With functional interfaces declared
        ToDoubleFunction<Book> priceOfBooksDoubleFunction = new ToDoubleFunction<Book>() {
            @Override
            public double applyAsDouble(Book book) {
                return book.price;
            }
        };
        DoubleConsumer averagePricePrintDoubleConsumer = new DoubleConsumer() {
            @Override
            public void accept(double averagePrice) {
                System.out.println(averagePrice);
            }
        };
        authors
            .stream()
            .map(authorBooksListFunction)
            .flatMap(streamAllBooksFunction)
            .mapToDouble(priceOfBooksDoubleFunction)
            .average()
            .ifPresent(averagePricePrintDoubleConsumer);

        banner("Average price for all books in the library - lambda");
        // SOLVED With functional interfaces used directly
        authors
            .stream()
            .map(author -> author.books)
            .flatMap(List::stream)
            .mapToDouble(book -> book.price)
            .average()
            .ifPresent(System.out::println);

        banner("Active authors that have at least one published book");
        // SOLVED With functional interfaces declared
        Predicate<Book> publishedBookPredicate = new Predicate<Book>() {
            @Override
            public boolean test(Book book) {
                return book.published;
            }
        };
        Predicate<Author> authorThatHasAtLeastOnePublishedBookPredicate = new Predicate<Author>() {
            @Override
            public boolean test(Author author) {
                return author.books.stream().anyMatch(publishedBookPredicate);
            }
        };
        authors
            .stream()
            .filter(activeAuthorsPredicate)
            .filter(authorThatHasAtLeastOnePublishedBookPredicate)
            .forEach(authorPrintConsumer);

        banner("Active authors that have at least one published book - lambda");
        // SOLVED With functional interfaces used directly
        authors.stream()
                .filter(author -> author.active)
                .filter(author -> author.books.stream().anyMatch(book -> book.published))
                .forEach(System.out::println);

    }

    private static void banner(final String m) {
        System.out.println("#### " + m + " ####");
    }
}


class Library {
    public static List<Author> getAuthors() {
        return Arrays.asList(
            new Author("Author A", true, Arrays.asList(
                new Book("A1", 100, true),
                new Book("A2", 200, true),
                new Book("A3", 220, true))),
            new Author("Author B", true, Arrays.asList(
                new Book("B1", 80, true),
                new Book("B2", 80, false),
                new Book("B3", 190, true),
                new Book("B4", 210, true))),
            new Author("Author C", true, Arrays.asList(
                new Book("C1", 110, true),
                new Book("C2", 120, false),
                new Book("C3", 130, true))),
            new Author("Author D", false, Arrays.asList(
                new Book("D1", 200, true),
                new Book("D2", 300, false))),
            new Author("Author X", true, Collections.emptyList()));
    }
}

class Author {
    String name;
    boolean active;
    List<Book> books;

    Author(String name, boolean active, List<Book> books) {
        this.name = name;
        this.active = active;
        this.books = books;
    }

    @Override
    public String toString() {
        return name + "\t| " + (active ? "Active" : "Inactive");
    }
}

class Book {
    String name;
    int price;
    boolean published;

    Book(String name, int price, boolean published) {
        this.name = name;
        this.price = price;
        this.published = published;
    }

    @Override
    public String toString() {
        return name + "\t| " + "\t| $" + price + "\t| " + (published ? "Published" : "Unpublished");
    }
}
