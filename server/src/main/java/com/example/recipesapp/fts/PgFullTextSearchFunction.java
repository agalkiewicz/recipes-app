package com.example.recipesapp.fts;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;

public class PgFullTextSearchFunction implements SQLFunction {

    @Override
    public String render(Type type, List args, SessionFactoryImplementor sessionFactoryImplementor) throws QueryException {
        if (args.size() < 2) {
            throw new IllegalArgumentException(
                    "The function must be passed 2 arguments");
        }

        String field = (String) args.get(0);
        String value = (String) args.get(1);
        String fragment = field + " @@ ";
        fragment += "plainto_tsquery('polish', " + value + ")";
        return fragment;

    }

    @Override
    public Type getReturnType(Type columnType, Mapping mapping)
            throws QueryException {
        return new BooleanType();
    }

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }
}
