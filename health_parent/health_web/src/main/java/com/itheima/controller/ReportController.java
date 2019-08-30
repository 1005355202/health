package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constants.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.OrderService;
import com.itheima.service.ReportService;
import com.itheima.service.SetMealService;
import com.itheima.util.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author: yp
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private OrderService orderService;

    @Reference
    private ReportService reportService;


    /**
     * 导出运营数据
     *
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        OutputStream os = null;
        XSSFWorkbook workbook = null;
        try {
            /**
             *  reportDate: null,     //日期
             todayNewMember: 0,     //新增会员数 (今天)
             totalMember: 0,        //总会员数
             thisWeekNewMember: 0, //本周新增会员数
             thisMonthNewMember: 0, //本月新增会员数
             todayOrderNumber: 0,  //今日预约数
             todayVisitsNumber: 0, //今日到诊数
             thisWeekOrderNumber: 0, //本周预约数
             thisWeekVisitsNumber: 0, //本周到诊数
             thisMonthOrderNumber: 0, //本月预约数
             thisMonthVisitsNumber: 0, //本月到诊数
             hotSetmeal: [ //热门套餐
             */
            //1.查询出报表数据
            Map<String, Object> result = reportService.getBusinessReportData();
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //2.根据Excel模版创建POI对象(XSSF)
            //2.1 创建工作簿对象
             is = request.getSession().getServletContext().getResourceAsStream("template/report_template.xlsx");
             workbook = new XSSFWorkbook(is);
            //2.2 获得工作表
            XSSFSheet sheet = workbook.getSheetAt(0);
            //3.取出数据, 向Excel里面插入数据
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }


            //4.通过流, 响应【下载 两头一流】
            os = response.getOutputStream();
            //两头(告诉浏览器去下载, 告诉浏览器文件的mime类型)
            response.setHeader("Content-Disposition","attachment;filename=report.xlsx"); //告诉浏览器去下载
            response.setHeader("Content-Type","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //告诉浏览器文件的mime类型(Excel)
            workbook.write(os);



            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                if(os != null){
                    os.close();
                }
                if(is != null){
                    is.close();
                }
                if(workbook != null){
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 运营数据统计
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 套餐统计
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            //setmealNames==>List<String> setmealNames
            //setmealCount===>List<Map>setmealCount
            //1.调用业务 获得套餐数据
            List<Map> setmealCount = orderService.getSetmealReport();
            //2.取出setmealCount里面的name 封装setmealNames
            List<String> setmealNames = new ArrayList<String>();
            for (Map map : setmealCount) {
                setmealNames.add(map.get("name").toString());
            }

            //3.再封装到Map
            Map resultMap = new HashMap();
            resultMap.put("setmealNames", setmealNames);
            resultMap.put("setmealCount", setmealCount);

            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }


    /**
     * 通过动态的日期,获取对应的会员数量饼状图
     * @param dates
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(@RequestBody ArrayList<Calendar> dates) {
        Calendar end = null;
        int month = 12;
        try {
            //不为空
            if (dates != null && dates.size() > 0) {
                Calendar start = dates.get(0);
                end = dates.get(1);
                int endMonth = end.get(Calendar.MARCH);
                int endYear = end.get(Calendar.YEAR);
                int startMonth = start.get(Calendar.MARCH);
                int startYear = start.get(Calendar.YEAR);
                month = endYear * 12 + endMonth - (startYear * 12 + startMonth);
            } else { //为空
                end = Calendar.getInstance();
            }
            end.add(Calendar.MARCH, -month);
            List<String> monthList = new ArrayList<>();
            for (int i = 0; i < month; i++) {
                end.add(Calendar.MONTH, 1);
                //得到前面几个月到现在的年月份
                monthList.add(new SimpleDateFormat("yyyy-MM").format(end.getTime()));
            }
            //通过月份去查询对应的会员数量
            List<Integer> countList = memberService.getMemberReport(monthList);

            Map<String, Object> map = new HashMap<>();
            map.put("months", monthList);
            map.put("memberCount", countList);

            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }


    public static void main(String[] args) throws Exception {
        List<String> monthsList = new ArrayList<String>();
        //获得当前时间
        Calendar calendar = Calendar.getInstance();
        //时间后退1年(12个月)
        calendar.add(Calendar.MONTH, -12);
        //遍历, 每遍历一次+1个月
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            String monthStr = DateUtils.parseDate2String(calendar.getTime(), "yyyy-MM");
            monthsList.add(monthStr);
        }

        System.out.println(monthsList);


    }

    //获取年龄和性别的数据
    @RequestMapping("/getSexandAge")
    public Result getSexandAge() {
        try {
            //调用service获取数据
      List<Map> mapList =  reportService.getSexandAge();
            List<String> list = new ArrayList<>();

            for (Map map : mapList) {
                System.out.println("map = " + map);


            }
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS,mapList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }

    }
}
