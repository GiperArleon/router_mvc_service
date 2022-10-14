package com.router.tools;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.router.api.telegram.bot.BotTextConstants.*;

public class Utils {
    public static final int MINUTES_IN_HOUR = 60;
    public static final int MINUTES_IN_DAY = 1440;
    public static final int MAX_TEXT_SIZE = 50;

    public static boolean onlyEnglishRussianLetters(String str) {
        if(str == null)
            return false;
        Pattern pattern = Pattern.compile("^[^0-9() \n][а-яА-Яa-zA-Z(0-9)]+$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String getUserName(Message msg) {
        return getUserName(msg.getFrom());
    }

    public static String getUserName(User user) {
        return (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    public static String getUserFullDescription(User user) {
        return (user.getUserName() != null) ? user.getUserName() + " id = " + user.getId():
                String.format("%s %s %s", user.getLastName(), user.getFirstName(), user.getId());
    }

    public static String validateTime(Integer hours, Integer minutes) {
        int dayTime = hours*MINUTES_IN_HOUR + minutes;
        if(hours<0 || minutes<0)
            return VALIDATOR_NEGATIVE_TIME;
        if(minutes>MINUTES_IN_HOUR)
            return VALIDATOR_MAX_MINUTES;
        if(dayTime>MINUTES_IN_DAY)
            return VALIDATOR_MAX_TIME;
        return VALIDATOR_OK;
    }

    public static String validateText(String text) {
        if(text == null)
            return VALIDATOR_ERROR;

        if(text.length() > MAX_TEXT_SIZE)
            return VALIDATOR_MAX_TEXT_SIZE;

        if(!onlyEnglishRussianLetters(text))
            return VALIDATOR_NO_SPECIAL;

        return VALIDATOR_OK;
    }
}
