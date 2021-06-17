package com.exqzore.intacle.controller.command;

import com.exqzore.intacle.controller.command.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Optional;

public enum CommandProvider implements Command {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    TO_LOGIN_PAGE(new GoLoginCommand()),
    REGISTER(new RegisterCommand()),
    TO_REGISTRATION_PAGE(new GoRegisterCommand()),
    ACTIVATE_USER(new ActivateUserCommand()),
    SHOW_PROFILE(new ShowProfileCommand()),
    EDIT_PROFILE(new EditProfileCommand()),
    SUBSCRIBE(new SubscribeCommand()),
    UNSUBSCRIBE(new UnsubscribeCommand()),
    SHOW_SUBSCRIBERS(new ShowSubscribersCommand()),
    CHANGE_LANGUAGE(new ChangeLanguageCommand()),
    SHOW_SUBSCRIPTIONS(new ShowSubscriptionsCommand()),
    SHOW_MESSENGER(new ShowMessengerCommand()),
    SHOW_CHAT(new ShowChatCommand()),
    CREATE_CHAT(new CreateChatCommand()),
    CREATE_MESSAGE(new CreateMessageCommand()),
    FIND_NEW_MESSAGES(new FindNewMessages()),
    FIND_USERS(new FindUsersCommand()),
    LIKE_COMMENT(new LikeCommentCommand()),
    UNLIKE_COMMENT(new UnlikeCommentCommand()),
    LIKE_ENTRY(new LikeEntryCommand()),
    UNLIKE_ENTRY(new UnlikeEntryCommand()),
    TAKE_FILE(new TakeFileCommand()),
    CHANGE_AVATAR_IMAGE(new ChangeAvatarImageCommand());

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
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return command.execute(request, response);
    }
}
