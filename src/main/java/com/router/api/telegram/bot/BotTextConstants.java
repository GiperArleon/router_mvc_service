package com.router.api.telegram.bot;

public class BotTextConstants {
    public static final String WRONG_COMMAND = "Сообщение, не соответствующее формату\nВсе команды /help";
    public static final String START_COMMAND = "Сначала нужно зарегистрироваться\n/reg Имя Фамилия\n/reg Иван Иванов\nВсе команды /help";
    public static final String WORK_COMMAND = "Вы уже зарегестрированы!\nВводите рабочее время\n/work часы минуты описание\n/work 3 20 мыл посуду\n/help все команды";
    public static final String SUCCESS_REG_COMMAND = "Регистрация успешна!\nВводите рабочее время\n/work часы минуты описание\n/work 3 20 мыл посуду";
    public static final String BOT_STARTED_MESSAGE = "TimeTracker бот запущен.\nСтарт по команде /start";
    public static final String BOT_OK_MESSAGE = "Успешно выполнено";
    public static final String BOT_ERROR_MESSAGE = "Произошла ошибка";
    public static final String BOT_ERROR_REG_WRONG_PARAMS = "Проверьте параметры\n/reg Имя Фамилия\n/reg Иван Иванов";
    public static final String BOT_ERROR_WORK_WRONG_PARAMS = "Проверьте параметры\n/work часы минуты описание\n/work 3 20 мыл посуду";
    public static final String BOT_ERROR_CHECK_WRONG_PARAMS = "Проверьте параметры\n/notify N проверки\n/notify 1\n/notify 3";
    public static final String NOT_ENOUGH_RIGHTS = "Не достатачно прав\n";

    public static final String VALIDATOR_OK = "OK";
    public static final String VALIDATOR_ERROR = "Ошибка";
    public static final String VALIDATOR_NEGATIVE_TIME = "Отрицательные часы или минуты";
    public static final String VALIDATOR_MAX_MINUTES = "Максимальное значение для минут 60";
    public static final String VALIDATOR_MAX_TIME = "Нельзя работать более 24 часов в день";
    public static final String VALIDATOR_MAX_TEXT_SIZE = "Максимум 50 символов";
    public static final String VALIDATOR_NO_SPECIAL = "Используйте только буквы и цифры, не используйте спец символы";

    public static final String HELP =
            "Бот учета времени\n\n" +
            "*Список команд*\n" +
            "\uD83D\uDC49/start - начало работы\n" +
            "\uD83D\uDC49/show - показать дела за сегодня\n" +
            "\uD83D\uDC49/help - помощь\n" +
            "\uD83D\uDC49/reg - регистрация\n     /reg Иван Иванов\n" +
            "\uD83D\uDC49/work - ввод времени\n     /work 2 30 мыл посуду\n" +
            "\uD83D\uDC49/notify N проверки\n     /notify 1     \n     /notify 3\n" +
            "\uD83D\uDC49/report - отчет за сегодня\n" +
            "\uD83D\uDC49/report pdf - отчет за сегодня в pdf\n\n" +
            "❗*Успей до 21:59* \uD83D\uDE42";
}
