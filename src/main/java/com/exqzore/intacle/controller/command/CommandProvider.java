package com.exqzore.intacle.controller.command;

import com.exqzore.intacle.controller.command.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

public enum CommandProvider implements Command{
    GO_TO_HOME_PAGE(GoToHomePageCommand.INSTANCE),
    GO_TO_CHAT_PAGE(GoToChatPageCommand.INSTANCE),

    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    TO_LOGIN_PAGE(new GoLoginCommand()),
    REGISTER(new RegisterCommand()),
    TO_REGISTRATION_PAGE(new GoRegisterCommand()),
    ACTIVATE_USER(new ActivateUserCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    SUBSCRIBE(new SubscribeCommand()),
    UNSUBSCRIBE(new UnsubscribeCommand()),
    SHOW_SUBSCRIBERS(new ShowSubscribersCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    SHOW_SUBSCRIPTIONS(new ShowSubscriptionsCommand()),
    SHOW_MESSENGER(new ShowMessengerCommand());

    private static final Logger logger = LogManager.getLogger();

    Command command;

    CommandProvider(Command command) {
        this.command = command;
    }

    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null || commandName.isBlank()) {
            logger.log(Level.INFO, "1");
            return Optional.empty();
        }
        commandName = commandName.toUpperCase(Locale.ROOT);
        Optional<Command> optionalCommand;
        try {
            logger.log(Level.INFO, "2");
            optionalCommand = Optional.of(CommandProvider.valueOf(commandName));
            logger.log(Level.INFO, "3");
        } catch (IllegalArgumentException exception) {
            logger.log(Level.INFO, "4");
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return command.execute(request);
    }
}
