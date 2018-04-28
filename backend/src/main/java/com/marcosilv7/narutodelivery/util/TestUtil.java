package com.marcosilv7.narutodelivery.util;

import org.apache.commons.text.RandomStringGenerator;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

public class TestUtil {
    public static final String EMAIL_USER="marcosilv7486@gmail.com";
    public static final String EMAIL_USER_BLOCKED="usuariobloqueado@gmail.com";
    public static final String PASSWORD_USER="123456";
    public static final Long USER_ID=1L;

    public static String generarCorreoRamdom(int cantidadCaracteres){
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        return generator.generate(cantidadCaracteres)+"@test.com";
    }
}
