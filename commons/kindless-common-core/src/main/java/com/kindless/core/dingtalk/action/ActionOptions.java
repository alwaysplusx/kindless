package com.kindless.core.dingtalk.action;

import com.kindless.core.dingtalk.Action;
import lombok.Data;
import org.apache.commons.cli.Options;

import java.util.Arrays;
import java.util.List;

@Data
public class ActionOptions implements Action {

    private String action;
    private List<String> alias;
    private Options options;

    public ActionOptions() {
    }

    public ActionOptions(String action, Options options, String... alias) {
        this.action = action;
        this.options = options;
        this.alias = Arrays.asList(alias);
    }

    @Override
    public String getAction() {
        return action;
    }

}
