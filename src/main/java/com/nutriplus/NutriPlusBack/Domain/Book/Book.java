package com.nutriplus.NutriPlusBack.Domain.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;


@Setter
@Getter
@NodeEntity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String isn;
    private String title;
    private String publisher;
    private String[] authors;
    private String publishedDate;


    public Book(String isn, String title, String publisher, String[] authors, String publishDate) {
        this.isn = isn;
        this.title = title;
        this.publisher = publisher;
        this.authors = authors;
        this.publishedDate = publishDate;
    }

    public Book() {}

}
