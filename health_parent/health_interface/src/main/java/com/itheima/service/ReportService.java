package com.itheima.service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: yp
 */
public interface ReportService {
    Map<String,Object> getBusinessReportData() throws Exception;

    List<Map> getSexandAge() throws Exception;

}
