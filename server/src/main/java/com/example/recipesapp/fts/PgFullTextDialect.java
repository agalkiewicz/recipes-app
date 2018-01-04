package com.example.recipesapp.fts;

import org.hibernate.dialect.PostgreSQL94Dialect;

public class PgFullTextDialect extends PostgreSQL94Dialect {

    public PgFullTextDialect() {
        registerFunction("fts", new PgFullTextSearchFunction());
    }
}
