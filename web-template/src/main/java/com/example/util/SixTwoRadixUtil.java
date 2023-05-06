package com.example.util;

/**
 * 使用 62进制 进行编码
 *
 * @author WuQinglong
 * @since 2023/1/5 14:45
 */
public class SixTwoRadixUtil {

    private static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static final int SCALE = 62;

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        do {
            long ret = num % SCALE;
            sb.append(CHARS[(int) ret]);
            num = num / SCALE;
        } while (num > SCALE - 1);
        if (num > 0) {
            sb.append(CHARS[(int) num]);
        }
        return sb.toString();
    }

}
