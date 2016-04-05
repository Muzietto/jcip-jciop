/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.quickcheck.firstTests;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marco Faustinelli (Muzietto) on 05/04/2016.
 */
@RunWith(JUnitQuickcheck.class)
public class SymmetricKeyCryptographyProperties {
    @Property
    public void decryptReversesEncrypt(String plaintext, String key)
            throws Exception {

        Crypto crypto = new Crypto();

        byte[] ciphertext =
                crypto.encrypt(plaintext.getBytes("UTF-8"), key);

        assertEquals(
                plaintext,
                new String(crypto.decrypt(ciphertext, key)));
    }


}