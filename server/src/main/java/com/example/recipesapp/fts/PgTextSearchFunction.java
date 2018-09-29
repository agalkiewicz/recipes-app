package com.example.recipesapp.fts;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PgTextSearchFunction implements SQLFunction {

    @Override
    public String render(Type type, List args, SessionFactoryImplementor sessionFactoryImplementor) throws QueryException {
        if (args.size() < 1) {
            throw new IllegalArgumentException(
                    "The function has 1 argument.");
        }

        String value = (String) args.get(0);

        String[] valuesArray = value
                .replaceAll("'", "")
                .split(", ");
        List<String> valuesList = new ArrayList<>(Arrays.asList(valuesArray));


        StringBuilder query = new StringBuilder("\'");
        for (Iterator<String> i = valuesList.iterator(); i.hasNext(); ) {
            String phrase = i.next();
            List<String> phraseArray = new ArrayList<>(Arrays.asList(phrase.split(" ")));

            if (phraseArray.size() > 1) {
                query.append("(");
                for (Iterator<String> j = phraseArray.iterator(); j.hasNext(); ) {
                    query.append(j.next());

                    if (j.hasNext()) {
                        query.append(" & ");
                    }
                }
                query.append(")");
            } else {
                query.append(phraseArray.get(0));
            }
            if (i.hasNext()) {
                query.append(" | ");
            }
        }
        query.append("\'");

        return "tokens @@ to_tsquery('polish', " + query.toString() + ")";
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
