/*
 * Project: jcip-jciop
 * Author: Marco Faustinelli - Muzietto (contacts@faustinelli.net)
 * Web: http://faustinelli.wordpress.com/, http://www.github.com/muzietto, http://faustinelli.net/
 * Version: 1.0
 * The GPL 3.0 License - Copyright (c) 2015-2016 - The jcip-jciop Project
 */

package net.faustinelli.quickcheck.firstTests;

import java.io.UnsupportedEncodingException;

/**
 * Created by Marco Faustinelli (Muzietto) on 05/04/2016.
 */
public class Crypto {
    byte[] encrypt(byte[] plaintext, String key) throws UnsupportedEncodingException {
        return (new String(plaintext) + key).getBytes("UTF-8");
    }

    byte[] decrypt(byte[] ciphertext, String key) throws UnsupportedEncodingException {
        return new String(ciphertext).substring(0, ciphertext.length - key.length()).getBytes("UTF-8");
    }
}
