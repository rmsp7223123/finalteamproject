package com.example.finalteamproject.chat;

public class HangulUtils {
    public static String convertToHangulInitialSound(String input) {
        if (input == null || input.length() == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (isHangul(ch)) {
                int unicode = ch - '가';

                int initialSound = unicode / 588;
                if (initialSound >= 0 && initialSound < Constants.INITIAL_SOUND.length) {
                    builder.append(Constants.INITIAL_SOUND[initialSound]);
                }
            } else {
                builder.append(ch);
            }
        }

        return builder.toString();
    }

    private static boolean isHangul(char c) {
        return (c >= 0xAC00 && c <= 0xD7A3);
    }
}
