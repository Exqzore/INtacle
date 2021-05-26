package com.exqzore.intacle.controller.command;

import com.exqzore.intacle.controller.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

public enum CommandProvider implements Command{
    GO_TO_HOME_PAGE(GoToHomePageCommand.INSTANCE),
    GO_TO_CHAT_PAGE(GoToChatPageCommand.INSTANCE),

    LOGIN(new LoginCommand()),
    TO_LOGIN_PAGE(new GoLoginCommand()),
    REGISTER(new RegisterCommand()),
    TO_REGISTRATION_PAGE(new GoRegisterCommand()),
    ACTIVATE_USER(new ActivateUserCommand()),
    SHOW_PROFILE(new ShowProfileCommand());

    Command command;

    CommandProvider(Command command) {
        this.command = command;
    }

    public static Optional<Command> defineCommand(String commandName) {
        if (commandName == null || commandName.isBlank()) {
            return Optional.empty();
        }
        commandName = commandName.toUpperCase(Locale.ROOT);
        Optional<Command> optionalCommand;
        try {
            optionalCommand = Optional.of(CommandProvider.valueOf(commandName));
        } catch (IllegalArgumentException exception) {
            optionalCommand = Optional.empty();
        }
        return optionalCommand;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return command.execute(request);
    }
}
