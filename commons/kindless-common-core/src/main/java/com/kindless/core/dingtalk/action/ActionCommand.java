package com.kindless.core.dingtalk.action;

import com.kindless.core.dingtalk.Action;
import lombok.Data;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

@Data
public class ActionCommand implements Action {

    private String[] commands;
    private ActionOptions options;
    private CommandLine cli;

    public ActionCommand() {
    }

    public ActionCommand(ActionOptions options, String[] commands) throws ParseException {
        this.options = options;
        this.commands = commands;
        this.cli = new DefaultParser().parse(options.getOptions(), commands);
    }

    public String getOptionValue(String name) {
        return getOptionValue(name, null);
    }

    public String getOptionValue(String name, String defaultValue) {
        return cli.getOptionValue(name, defaultValue);
    }

    @Override
    public String getAction() {
        return options.getAction();
    }

}
