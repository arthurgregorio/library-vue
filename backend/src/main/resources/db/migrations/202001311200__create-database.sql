/*
Before starting!

Run this command as super(admin|user|root) in your Postgres database, directly on the database you are going to use
for this application

-- below here, run as admin
CREATE EXTENSION IF NOT EXISTS unaccent WITH SCHEMA pg_catalog;

CREATE OR REPLACE FUNCTION filter(needles text, VARIADIC haystacks text [])
RETURNS boolean AS $$
SELECT needles IS NULL OR trim(needles) = '' OR EXISTS(
    SELECT DISTINCT 1
    FROM unnest(haystacks) haystack,
    unnest(string_to_array(needles, ',')) needle
    WHERE unaccent(haystack) ILIKE '%' || unaccent(needle) || '%');
$$ LANGUAGE SQL;
*/