//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service.file;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.maintenance.common.FileType;
import com.rest.api.exception.ValidationException;

@Component
public class FileServiceImpl {

    @Resource
    private Map<String, FileTypeHandler> fileTypeHandlerMap;

    public File getFile(String type, Long id) {
        FileType fileType = FileType.valueOf(type.toUpperCase());
        if (fileType == null) {
            throw new ValidationException("type", type, "invalid type");
        }
        FileTypeHandler fileHandler = fileTypeHandlerMap.get(fileType.name());
        return fileHandler.getFile(id);
    }



}
