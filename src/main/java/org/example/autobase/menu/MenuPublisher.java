package org.example.autobase.menu;

import java.util.List;

public class MenuPublisher {

    private static final String ACTION_STRING = "To do action press the number";
    private static final String MANAGE_REQUESTS = "Manage the requests";
    private static final String CHECK_STATISTICS = "Check the statistics";
    private static final String NEXT_DAY = "Go to next day";
    private static final String SHOW_GOODS_TRANSPORTED_BY_EACH_DRIVER = "Show amount of goods transported by each driver";
    private static final String SHOW_GOODS_TRANSPORTED_TO_DESTINATION = "Show amount of goods transported to exact destination";
    private static final String SHOW_DRIVER_WITH_MOST_BALANCE = "Show driver with the most balance";
    private static final String INVATION_STRING = "Please enter the number";
    private static final String BACK_STRING = "Go back to previous menu";
    private static final String EXIT_STRING = "Close the program";
    private static final String SEPARATOR = "-";
    private static final String DOT_SPACE = ".  ";
    private static final String END_LINE = "\n";

    public static void showMenu() {
        String resultString = ACTION_STRING +
                END_LINE +
                showStringList(List.of(MANAGE_REQUESTS, CHECK_STATISTICS, NEXT_DAY)) +
                -1 +
                DOT_SPACE +
                EXIT_STRING +
                END_LINE +
                SEPARATOR.repeat(60) +
                END_LINE +
                INVATION_STRING;
        System.out.println(resultString);
    }

    public static void showStatsMenu(){
        String resultString = ACTION_STRING +
                END_LINE +
                showStringList(List.of(SHOW_GOODS_TRANSPORTED_BY_EACH_DRIVER,
                        SHOW_GOODS_TRANSPORTED_TO_DESTINATION, SHOW_DRIVER_WITH_MOST_BALANCE)) +
                -1 +
                DOT_SPACE +
                BACK_STRING +
                END_LINE +
                SEPARATOR.repeat(60) +
                END_LINE +
                INVATION_STRING;
        System.out.println(resultString);
    }

    public static String showStringList(List<String> sourceStringList) {
        int menuLine = 1;
        StringBuilder resultString = new StringBuilder();

        resultString.append(SEPARATOR.repeat(60))
                .append(END_LINE);

        for (var currentString : sourceStringList) {
            resultString.append(menuLine++)
                    .append(DOT_SPACE)
                    .append(currentString)
                    .append(END_LINE);
        }
        resultString.append(END_LINE);

        return resultString.toString();
    }

    private MenuPublisher() {
    }
}
