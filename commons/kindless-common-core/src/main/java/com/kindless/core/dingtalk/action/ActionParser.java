package com.kindless.core.dingtalk.action;

import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActionParser {

    private Pattern pattern = Pattern.compile("-\\w* ");
    private Map<String, ActionOptions> optionMap = new HashMap<>();

    public ActionCommand parse(String command) {
        ActionOptions options = null;
        String[] commands = new String[0];
        for (String action : optionMap.keySet()) {
            if (command.startsWith(action)) {
                String commandString = command.substring(action.length()).trim();
                commands = toCommands(commandString);
                options = optionMap.get(action);
                break;
            }
        }
        if (options == null) {
            throw new IllegalArgumentException("unknown action");
        }
        try {
            return new ActionCommand(options, commands);
        } catch (ParseException e) {
            throw new IllegalArgumentException("illegal command", e);
        }
    }

    public void addActionOptions(ActionOptions... options) {
        for (ActionOptions option : options) {
            for (String alias : option.getAlias()) {
                optionMap.put(alias, option);
            }
            optionMap.put(option.getAction(), option);
        }
    }

    private String[] toCommands(String commandString) {
        List<String> commands = new ArrayList<>();
        Matcher matcher = pattern.matcher(commandString);
        int index = 0;
        while (matcher.find()) {
            if (index > 0) {
                commands.add(commandString.substring(index, matcher.start()).trim());
            }
            commands.add(matcher.group().trim());
            index = matcher.end();
        }
        if (index > 0) {
            commands.add(commandString.substring(index).trim());
        }
        return commands.toArray(new String[0]);
    }

}
