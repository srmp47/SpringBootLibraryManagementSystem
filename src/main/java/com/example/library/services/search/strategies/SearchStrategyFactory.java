package com.example.library.services.search.strategies;



import com.example.library.models.enums.SearchAlgorithm;
import com.example.library.services.search.strategies.impl.AuthorExactSearch;
import com.example.library.services.search.strategies.impl.AuthorIgnoreCaseSearch;
import com.example.library.services.search.strategies.impl.TitleExactSearch;
import com.example.library.services.search.strategies.impl.TitleIgnoreCaseSearch;


public class SearchStrategyFactory {

    public static SearchStrategy createStrategy(SearchAlgorithm algorithm) {
        return switch (algorithm) {
            case SEARCH_BY_TITLE_EXACT -> new TitleExactSearch();
            case SEARCH_BY_AUTHOR_EXACT -> new AuthorExactSearch();
            case SEARCH_BY_TITLE_IGNORE_CASE -> new TitleIgnoreCaseSearch();
            case SEARCH_BY_AUTHOR_IGNORE_CASE -> new AuthorIgnoreCaseSearch();
        };
    }
}