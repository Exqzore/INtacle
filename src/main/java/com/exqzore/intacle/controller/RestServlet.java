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

@WebServlet("/rest")
public class RestServlet extends HttpServlet{
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCODING = "UTF-8";
    private static final String COMMAND = "command";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.INFO, "REST (New request: Url = '{}')", request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        request.setCharacterEncoding(ENCODING);
        String commandId = request.getParameter(COMMAND);
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);
        if (commandOptional.isEmpty()) {
            logger.log(Level.ERROR, "Wrong command: {}", commandId);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.getRequestDispatcher(WebPagePath.ERROR_PAGE).forward(request, response);
            return;
        }
        Command command = commandOptional.get();
        logger.log(Level.INFO, "Command: {}", command);
        String json = command.execute(request, response);
        response.setContentType(CONTENT_TYPE);
        PrintWriter printWriter = response.getWriter();
        printWriter.print(json);
        printWriter.flush();
    }
}
