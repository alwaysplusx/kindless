package com.kindless.todo.dingtalk;

import com.kindless.core.dingtalk.action.ActionOptions;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Option.Builder;

public class Options {

    public static ActionOptions addAction() {
        return new ActionOptions(
                "add",
                new org.apache.commons.cli.Options()
                        .addOption(TITLE_OPTION)
                        .addOption(MESSAGE_OPTION)
                        .addOption(DEADLINE_OPTION)
                        .addOption(HELP_OPTION)
        );
    }

    public static ActionOptions deleteAction() {
        return new ActionOptions(
                "delete",
                new org.apache.commons.cli.Options()
                        .addOption(ID_OPTION)
                        .addOption(HELP_OPTION),
                "del"
        );
    }

    public static ActionOptions listAction() {
        return new ActionOptions(
                "list",
                new org.apache.commons.cli.Options()
                        .addOption(TYPE_OPTION)
                        .addOption(SIZE_OPTION)
        );
    }

    private static final Option TITLE_OPTION = newOption("t").hasArg().argName("title").longOpt("title").desc("待办标题").build();
    private static final Option SIZE_OPTION = newOption().hasArg().argName("5").longOpt("size").desc("获取的数据量大小").build();
    private static final Option DEADLINE_OPTION = newOption().hasArg().argName("2020-02-02 | 2020-02-02 02:02:02").longOpt("deadline").desc("待办的截止时间").build();
    private static final Option ID_OPTION = newOption().hasArg().argName("id").longOpt("id").desc("待办ID").build();
    private static final Option MESSAGE_OPTION = newOption("m").hasArg().argName("message").longOpt("message").desc("待办的具体描述").build();
    private static final Option TYPE_OPTION = newOption().hasArg().argName("all | done | undone").longOpt("type").desc("数据类型").build();
    private static final Option HELP_OPTION = newOption("h").longOpt("help").desc("帮助信息").build();

    private static Builder newOption() {
        return Option.builder();
    }

    private static Builder newOption(String name) {
        return Option.builder(name);
    }

}
