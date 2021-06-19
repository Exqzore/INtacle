package com.exqzore.intacle.controller;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.FileService;
import com.exqzore.intacle.service.impl.FileServiceImpl;
import com.exqzore.intacle.service.util.FileNameGenerator;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final static ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

    private final static FileService fileService = FileServiceImpl.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "UPLOAD (New request: Url = '{}')", request.getRequestURL() + "?" + request.getQueryString());

        try (PrintWriter responseWriter = response.getWriter()) {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            logger.log(Level.INFO, "1");
            if (fileItems.size() != 1 || fileItems.get(0).isFormField()) {
                logger.log(Level.INFO, "2");
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }
            logger.log(Level.INFO, "3");
            FileItem fileItem = fileItems.get(0);
            logger.log(Level.INFO, fileItem);
            String filePath = FileNameGenerator.generate(FileNameGenerator.getExtension(fileItem.getName()));
            logger.log(Level.INFO, filePath);
            fileService.writeFile(fileItem, filePath);
            responseWriter.write(filePath.replace('\\', '/'));
        } catch (FileUploadException | ServiceException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
