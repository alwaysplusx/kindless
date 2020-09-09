package com.kindless.gateway.extract;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuxin
 */
@Data
public abstract class HttpInfo {

    String remoteAddress;
    String localAddress;

    String version;
    Map<String, List<String>> headers;

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = new LinkedHashMap<>();
        List<String> keys = new ArrayList<>(headers.keySet());
        keys.sort(String::compareTo);
        for (String key : keys) {
            this.headers.put(key, headers.get(key));
        }
    }

    protected void appendHeaders(StringBuilder o) {
        if (headers != null) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    o.append(key).append(": ").append(value).append("\n");
                }
            }
        }
    }

}
