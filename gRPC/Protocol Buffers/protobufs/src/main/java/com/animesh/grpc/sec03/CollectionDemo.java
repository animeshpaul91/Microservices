package com.animesh.grpc.sec03;

import com.animesh.grpc.sec03.models.Book;
import com.animesh.grpc.sec03.models.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CollectionDemo {
    private static final Logger log = LoggerFactory.getLogger(CollectionDemo.class);

    public static void main(String[] args) {
        final Book book1 = Book.newBuilder()
                .setTitle("Effective Java")
                .setAuthor("Joshua Bloch")
                .setPublicationYear(2001)
                .build();

        final Book book2 = Book.newBuilder()
                .setTitle("Clean Code")
                .setAuthor("Robert C. Martin")
                .setPublicationYear(2008)
                .build();

        final List<Book> books = List.of(book1, book2);
        final Library library = Library.newBuilder()
                .setName("City Library")
                .addAllBooks(books)
                .build();

        log.info("Library: {}", library);
    }
}
