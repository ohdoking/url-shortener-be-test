package com.github.vivyteam.url.util;

import org.springframework.stereotype.Component;

import java.util.Stack;

@Component
public class Base62Util {
    private static final int BASE = 62;
    private static String CODES = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public String encode(long value){
        long quotient = value;
        StringBuilder sbEncodedValue = new StringBuilder();
        while (quotient >= BASE){
            sbEncodedValue = sbEncodedValue.append(CODES.charAt((int) (quotient % BASE)));
            quotient = (long) Math.floor(quotient / BASE);
        }
        sbEncodedValue.append(CODES.charAt((int) quotient));

        return sbEncodedValue.reverse().toString();
    }

    public long decode(String encodedValue){
        long decodedValue = 0;
        long power = 1;

        for (int i = encodedValue.length() - 1 ; i >= 0; i--) {
            decodedValue += CODES.indexOf(encodedValue.charAt(i)) * power;
            power *= BASE;
        }

        return decodedValue;
    }
}
