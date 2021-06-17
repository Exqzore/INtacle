package com.exqzore.intacle.controller;

import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.entity.User;
import com.exqzore.intacle.service.FileService;
import com.exqzore.intacle.service.impl.FileServiceImpl;
import com.exqzore.intacle.service.util.FileName;
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
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private final static ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

    private final static FileService fileService = FileServiceImpl.getInstance();

    private static final String USER = "user";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.INFO, "UPLOAD (New request: Url = '{}')", request.getRequestURL() + "?" + request.getQueryString());

        User user = (User) request.getSession().getAttribute(USER);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_IMPLEMENTED);
            return;
        }
        try (PrintWriter responseWriter = response.getWriter()) {
            List<FileItem> fileItems = servletFileUpload.parseRequest(request);
            if (fileItems.size() != 1 || fileItems.get(0).isFormField()) {
                response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                return;
            }
            FileItem fileItem = fileItems.get(0);
            String filePath = FileName.generate(user.getLogin() + File.separator, FileName.getExtension(fileItem.getName()));
            fileService.writeFile(fileItem, filePath);
            responseWriter.write(filePath.replace('\\', '/'));
        } catch (FileUploadException | ServiceException exception) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
