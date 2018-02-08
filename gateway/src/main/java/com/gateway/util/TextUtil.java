package com.gateway.util;

import org.springframework.stereotype.Component;

@Component
public class TextUtil {

    /**
     * Extract json on the string.
     *
     * @param str
     * @param numberOfEscapeCharacter
     * @return
     */
    public static String extractJson(String str, int numberOfEscapeCharacter) {
        return str.substring(str.indexOf("{"), str.indexOf("}") + numberOfEscapeCharacter);
    }
}
