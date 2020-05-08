package com.nutriplus.NutriPlusBack.Repositories;

import com.nutriplus.NutriPlusBack.Domain.Book.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ApplicationBookRepository extends Neo4jRepository<Book, Long> {
    Book findByTitle(String title);
    Book findByIsn(String isn);
}


