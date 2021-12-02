package cn.arning.jgit.utils;

import org.springframework.lang.Nullable;

/**
 * @author arning
 */
public abstract class Assert {
    public Assert() {
    }

    public static void isNotNull(@Nullable Object obj, String massage) {
        if (null == obj) {
            throw new IllegalArgumentException(massage);
        }
    }

}
