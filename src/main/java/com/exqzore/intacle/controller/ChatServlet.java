package com.exqzore.intacle.controller;

import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.controller.command.CommandProvider;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/chats")
public class ChatServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();
    private static final String ENCODING = "UTF-8";
    private static final String COMMAND = "command";

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

        System.out.println("NEW url is: " + request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        System.out.println("Requested Session Id is " + request.getRequestedSessionId());

        request.setCharacterEncoding(ENCODING);

        String commandId = request.getParameter(COMMAND);
        if(commandId != null) {
            if (commandId.contains("getmsg")) {
                response.setContentType("application/json; charset=utf-8");
                PrintWriter printWriter = response.getWriter();
                printWriter.print("""
                        {
                          "name": "Иван",
                          "status": "ok"
                        }""");
                printWriter.flush();
                return;
            }
        }
        Optional<Command> commandOptional = CommandProvider.defineCommand(commandId);

        if (commandOptional.isEmpty()) {
            System.out.println("iimmmm");
//            logger.log(Level.ERROR, "wrong command ({})", commandId);
//            request.getRequestDispatcher(WebPagePath.ERROR).forward(request, response);
            request.getRequestDispatcher("/jsp/chat_page.jsp").forward(request, response);
            return;
        }

        Command command = commandOptional.get();
        logger.log(Level.INFO, "Command: {}", command);
        String page = command.execute(request);

        if (page.isEmpty()) {
            return;
        }

        if (!page.contains(request.getContextPath())) {
            request.getRequestDispatcher(page).forward(request, response);
        } else {
            logger.log(Level.INFO, "Redirected to {}", page);
            response.sendRedirect(page);
        }
    }

    @Override
    public void destroy() {
    }
}
