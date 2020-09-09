package com.kindless.gateway.extract;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wuxin
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseHttpInfo extends HttpInfo {

    String statusCode;

    String reason;

    @Override
    public String toString() {
        StringBuilder o = new StringBuilder();
        o.append(version).append(" ")
                .append(statusCode).append(" ")
                .append(reason).append("\n");
        this.appendHeaders(o);
        return o.toString();
    }

    protected boolean isDone() {
        return version != null && headers != null && statusCode != null && reason != null;
    }

}
