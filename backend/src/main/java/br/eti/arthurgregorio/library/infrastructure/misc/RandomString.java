package br.eti.arthurgregorio.library.infrastructure.misc;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2020
 */
public final class RandomString {

    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    public static final String DIGITS = "0123456789";

    public static final String ALPHANUM = UPPER + LOWER + DIGITS;

    /**
     *
     */
    public RandomString() {
        this(8);
    }

    /**
     *
     * @param length
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     *
     * @param length
     * @param random
     */
    public RandomString(int length, Random random) {
        this(length, random, ALPHANUM);
    }

    /**
     *
     * @param length
     * @param random
     * @param symbols
     */
    public RandomString(int length, Random random, String symbols) {

        if (length < 1) throw new IllegalArgumentException("Length should be > 0");
        if (symbols.length() < 2) throw new IllegalArgumentException("Length should be > 2");

        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     *
     * @return
     */
    public String nextString() {
        for (int idx = 0; idx < this.buf.length; ++idx) {
            this.buf[idx] = this.symbols[this.random.nextInt(this.symbols.length)];
        }
        return new String(this.buf);
    }
}