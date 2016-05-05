/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.quickcheck.firstTests;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

/**
 * Created by Marco Faustinelli (Muzietto) on 05/04/2016.
 * http://stackoverflow.com/questions/30211147/junit-quickcheck-generate-string-maching-a-pattern
 */
public class MyCharacterGenerator extends Generator<String> {

    private static final String LOWERCASE_CHARS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = ".-\\;:_@[]^/|}{";
    private static final String ALL_MY_CHARS = LOWERCASE_CHARS
            + UPPERCASE_CHARS + NUMBERS + SPECIAL_CHARS;
    public static final int CAPACITY = 40;

    public MyCharacters() {
        super(String.class);
    }

    @Override
    public String generate(SourceOfRandomness random, GenerationStatus status) {
        StringBuilder sb = new StringBuilder(CAPACITY);
        for (int i = 0; i < CAPACITY; i++) {
            int randomIndex = random.nextInt(ALL_MY_CHARS.length());
            sb.append(ALL_MY_CHARS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
