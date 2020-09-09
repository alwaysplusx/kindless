package com.kindless.gateway.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

/**
 * @author wuxin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RequestHttpInfo extends HttpInfo {

    String method;
    String path;
    Map<String, List<String>> queryParams;

    @Override
    public String toString() {
        StringBuilder o = new StringBuilder();
        o.append(remoteAddress).append(" <-> ").append(localAddress).append("\n");
        o.append(method).append(" ")
                .append(!path.startsWith("/") ? "/" : "").append(path)
                .append(" ").append(version).append("\n");
        this.appendHeaders(o);
        return o.toString();
    }

    protected boolean isDone() {
        return method != null
                && path != null
                && version != null
                && headers != null;
    }

}
