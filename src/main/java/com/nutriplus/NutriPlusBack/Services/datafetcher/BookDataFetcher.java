package com.nutriplus.NutriPlusBack.Services.datafetcher;

import com.nutriplus.NutriPlusBack.Domain.Book.Book;
import com.nutriplus.NutriPlusBack.Repositories.ApplicationBookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

    @Autowired
    ApplicationBookRepository applicationBookRepository;

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment) {
        String isn = dataFetchingEnvironment.getArgument("isn");
        return applicationBookRepository.findByIsn(isn);
    }
}
