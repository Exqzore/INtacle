package com.exqzore.intacle.controller;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.controller.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

//@WebServlet(urlPatterns = {"/", "/profile/*", "/main"})
@WebServlet("/main")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCODING = "UTF-8";
    private static final String COMMAND = "command";
    private final static String PREVIOUS_PAGE = "previous_page";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.INFO, "New GET (Url = {})", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        request.setCharacterEncoding(ENCODING);

        String commandId = request.getParameter(COMMAND);
        logger.log(Level.INFO, "COMMAND is {}", commandId);
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);
        logger.log(Level.INFO, "COMMAND is {}", commandOptional);

        if (commandOptional.isEmpty()) {
            logger.log(Level.ERROR, "wrong command ({})", commandId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//            request.getRequestDispatcher(WebPagePath.ERROR).forward(request, response);
            return;
        }

        Command command = commandOptional.get();
        logger.log(Level.INFO, "Command: {}", command);
        String page = command.execute(request);
        HttpSession session = request.getSession();
        session.setAttribute(PREVIOUS_PAGE, page);

        if (page == null || page.isEmpty()) {
            return;
        }

        if (!page.contains(request.getContextPath())) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            logger.log(Level.INFO, "Redirected to {}", page);
            response.sendRedirect(page);
        }
    }
}