package br.eti.arthurgregorio.library.infrastructure.jpa;

import org.hibernate.dialect.PostgreSQL95Dialect;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 10/01/2020
 */
public class CustomPostgresDialect extends PostgreSQL95Dialect {

    /**
     *
     */
    public CustomPostgresDialect() {
        super.registerFunction("FILTER", new PostgresFilterFunction());
    }
}
