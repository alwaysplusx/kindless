package com.kindless.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
public class DateFormatter {

    public static final Set<String> COMMON_DATE_FORMAT_PATTERN_SET;

    static {
        Set<String> patterns = new LinkedHashSet<>();
        patterns.add("yyyy-MM-dd HH:mm:ss");
        patterns.add("yyyy-MM-dd HH:mm:ss.SSS");
        patterns.add("yyyy-MM-dd");
        patterns.add("yyyy/MM/dd");
        patterns.add("dd/MM/yyyy");
        patterns.add("yyyy-MM-dd'T'HH:mm:ss");
        COMMON_DATE_FORMAT_PATTERN_SET = Collections.unmodifiableSet(patterns);
    }

    private Set<String> patterns;

    public DateFormatter(Set<String> patterns) {
        this.patterns = new LinkedHashSet<>(patterns);
    }

    public Date parse(String dateString) {
        if (dateString == null) {
            return null;
        }
        for (String pattern : patterns) {
            try {
                return new SimpleDateFormat(pattern).parse(dateString);
            } catch (ParseException e) {
                log.debug("dateString not match this format: {}", pattern, e);
            }
        }
        throw new IllegalArgumentException("unsupported date formatter: " + dateString);
    }

    public String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(pattern).format(date);
    }

}
