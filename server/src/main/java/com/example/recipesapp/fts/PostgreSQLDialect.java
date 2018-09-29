package com.example.recipesapp.fts;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class PostgreSQLDialect extends PostgreSQL94Dialect {

    public PostgreSQLDialect() {
        registerFunction("text_search", new PgTextSearchFunction());
    }
}
