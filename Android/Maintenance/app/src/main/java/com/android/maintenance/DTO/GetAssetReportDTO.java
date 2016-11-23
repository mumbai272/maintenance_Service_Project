package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by anand on 20-Nov-16.
 */
public class GetAssetReportDTO extends BaseResponseDTO implements Serializable {

    private AssetReportDTO assetReport;
    private List<ReportLogDTO> reportLog;
    private ReportSpareResponseDTO spares;
    private ReportChargesDTO reportCharge;


}
