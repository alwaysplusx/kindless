package com.kindless.core.lock;

public class LockKeys {

    private static final String PREFIX = "kindless:";

    public static final String PATTERN_OF_USER_CREATING_BY_ACCOUNT = PREFIX + "user:creating-by-account:%s:%s";

    public static final String PATTERN_OF_TODO_NEXT_SHORT_ID = PREFIX + "todo:next-short-id:%s";

}
