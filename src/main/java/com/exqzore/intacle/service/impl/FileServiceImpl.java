package com.exqzore.intacle.service.impl;

import com.exqzore.intacle.exception.PropertyReaderException;
import com.exqzore.intacle.exception.ServiceException;
import com.exqzore.intacle.service.FileService;
import com.exqzore.intacle.service.util.PropertyReader;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class FileServiceImpl implements FileService {
    private static final Logger logger = LogManager.getLogger();

    private static final FileService instance = new FileServiceImpl();

    private static final String IMAGE_UPLOAD_PROPERTY_PATH = "properties/image_upload.properties";
    private static final String IMAGE_UPLOAD_PATH = "path";
    private static final String PATH;

    private static final Properties properties;

    static {
        try {
            properties = PropertyReader.read(IMAGE_UPLOAD_PROPERTY_PATH);
            PATH = properties.getProperty(IMAGE_UPLOAD_PATH);
        } catch (NullPointerException | PropertyReaderException exception) {
            logger.log(Level.ERROR, "Property read error", exception);
            throw new RuntimeException(exception);
        }
    }

    private FileServiceImpl() {
    }

    public static FileService getInstance() {
        return instance;
    }

    @Override
    public void writeFile(FileItem fileItem, String fileName) throws ServiceException {
        try {
            String fullPath = PATH + File.separator + fileName;
            if (Files.exists(Path.of(fullPath))) {
                Files.delete(Path.of(fullPath));
            }
            fileItem.write(new File(fullPath));
            logger.log(Level.INFO, "New file {}", fullPath);
        } catch (Exception exception) {
            throw new ServiceException("Can not write file", exception);
        }
    }

    @Override
    public byte[] readFile(String fileName) throws ServiceException {
        byte[] result;
        String fileUri = PATH + File.separator + fileName;
        Path filePath = Path.of(fileUri);
        if (Files.exists(filePath)) {
            try {
                result = Files.readAllBytes(filePath);
            } catch (IOException exception) {
                throw new ServiceException("Cant read file", exception);
            }
        } else {
            throw new ServiceException("File not exists");
        }
        return result;
    }
}
