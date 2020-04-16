package br.eti.arthurgregorio.library.infrastructure.jpa;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 10/01/2020
 */
public class PostgresFilterFunction implements SQLFunction {

    /**
     * {@inheritDoc}
     *
     * @param firstArgumentType
     * @param arguments
     * @param factory
     * @return
     * @throws QueryException
     */
    @Override
    @SuppressWarnings("unchecked")
    public String render(final Type firstArgumentType, final List arguments, final SessionFactoryImplementor factory) throws QueryException {

        final String query = this.renderCast((String) arguments.get(0));

        final List<String> fields = (List<String>) arguments.stream().skip(1)
                .map(field -> this.renderCast((String) field))
                .collect(Collectors.toList());

        return String.format("FILTER(%s, %s)", query, String.join(", ", fields));
    }

    /**
     *
     * @param field
     * @return
     */
    private String renderCast(final String field) {
        return String.format("cast(%s as text)", field);
    }

    /**
     * {@inheritDoc}
     *
     * @param firstArgumentType
     * @param mapping
     * @return
     * @throws QueryException
     */
    @Override
    public Type getReturnType(final Type firstArgumentType, final Mapping mapping) throws QueryException {
        return new BooleanType();
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean hasArguments() {
        return true;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }
}