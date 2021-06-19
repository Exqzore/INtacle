package com.exqzore.intacle.controller.filter;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.WebPageRequest;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.controller.command.CommandProvider;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.entity.UserRole;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    private static final String ENCODING = "UTF-8";
    private static final String COMMAND = "command";
    private static final String USER = "user";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        request.setCharacterEncoding(ENCODING);
        String commandId = request.getParameter(COMMAND);
        User user = (User) request.getSession().getAttribute(USER);
        UserRole role = user != null ? user.getRole() : UserRole.GUEST;
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);
        if (commandOptional.isEmpty()) {
            filterChain.doFilter(request, response);
        } else {
            Command command = commandOptional.get();
            logger.log(Level.INFO, "Current role = '{}', Allowed access = '{}'", role, command.getAllowedAccessLevels());
            if (command.getAllowedAccessLevels().contains(role)) {
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                request.getRequestDispatcher(WebPagePath.ERROR_PAGE).forward(request, response);
            }
        }
    }
}
