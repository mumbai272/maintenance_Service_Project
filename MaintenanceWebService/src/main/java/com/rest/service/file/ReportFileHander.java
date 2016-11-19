//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service.file;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.FileType;
import com.rest.entity.AssetReport;
import com.rest.maintenance.config.SettingConfig;
import com.rest.repository.AssetReportRepository;
import com.rest.service.BaseServiceImpl;

@Component
public class ReportFileHander extends BaseServiceImpl implements FileTypeHandler {

    @Autowired
    private SettingConfig settingConfig;

    @Autowired
    private AssetReportRepository assetReportRepository;

    @Override
    public File getFile(Long id) {
        AssetReport report = assetReportRepository.findOne(id);
        if (report == null) {
            throw new RuntimeException("report does not exists");
        }
        if (StringUtils.isBlank(report.getReportGenarated())) {
            throw new RuntimeException("Report is not generated");
        }
        StringBuilder fileName = new StringBuilder(settingConfig.getRootFolder());
        fileName.append(File.separator).append(report.getAssetLog().getClientId()).append(FileType.REPORT.getFilePath()).
        append(File.separator).append(report.getLogId()).append(File.separator)
                .append(report.getReportGenarated());
        File file = new File(fileName.toString());
        return file;
    }

}
