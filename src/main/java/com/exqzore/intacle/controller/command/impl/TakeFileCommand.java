package com.exqzore.intacle.controller.command.impl;

import com.exqzore.intacle.controller.WebPagePath;
import com.exqzore.intacle.controller.command.Command;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.FileService;
import com.exqzore.intacle.service.impl.FileServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TakeFileCommand implements Command {
    private static final Logger logger = LogManager.getLogger();

    private static final String FILE_NAME = "file_name";
    private static final FileService fileService = FileServiceImpl.getInstance();
    private static final String CAUSE_HEADER = "cause";

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter(FILE_NAME);
        if (fileName.isEmpty()) {
            return WebPagePath.EMPTY_PAGE;
        }
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(fileService.readFile(fileName));
        } catch (IOException | ServiceException exception) {
            logger.log(Level.ERROR, exception);
            response.addHeader(CAUSE_HEADER, exception.getMessage());
        }
        return WebPagePath.EMPTY_PAGE;
    }
}
