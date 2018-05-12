package com.minimall.boilerplate;

import com.minimall.boilerplate.common.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.minimall.boilerplate.common.Constants.*;
import static java.util.Objects.nonNull;

/**
 * Title:定时任务
 * <p>Description: </p>

 */
@Configuration
@EnableScheduling
public class SchedulingConfiguration {

  /*@Autowired
  AttendanceMonthRepository attendanceMonthRepository;
  @Autowired
  AttendanceService attendanceService;
  @Autowired
  AttendanceDailyRepository attendanceDailyRepository;
  @Autowired
  AttendanceApplyRepository attendanceApplyRepository;
  @Autowired
  EmployeeInfoRepository employeeInfoRepository;

  *//**
   *每日定时
   *//*
  @Transactional
  @Scheduled(cron = "0 0 7 * * ?") // 每天7点执行一次
  public void dailySchedule() {
    Timestamp timestamp=new Timestamp(System.currentTimeMillis()-60*60*1000*24);//当执的时候一定是统计日已经过去所以取前一天的时间
    String attDay=DateHelper.timeStampFormater(timestamp,"yyyy-MM-dd 00:00:00");//获得日参数
    List<AttendanceDaily> attendanceDailies=new ArrayList<>();
    List<EmployeeInfo> employeeInfos=employeeInfoRepository.findByStatus(1);
    for(int i=0;i<employeeInfos.size();i++) {
      EmployeeInfo employeeInfo = employeeInfos.get(i);
      //日统计
      AttendanceDaily attendanceDaily=new AttendanceDaily();
      AttendanceDaily attendanceDailyOld=attendanceDailyRepository.findByEmployeeInfoIdAndAttTime(employeeInfo.getId(),Timestamp.valueOf(attDay));
      if(nonNull(attendanceDailyOld)) attendanceDaily=attendanceDailyOld;
      attendanceDaily.setAttTime(Timestamp.valueOf(attDay));
      List<AttendanceDTO> attendanceDTOS = attendanceService.findAttTime(timestamp, employeeInfo.getCompanyInfo().getId(), employeeInfo.getId());
      if(attendanceDTOS.size()>=1){
        if(attendanceDTOS.size()==1){
          attendanceDaily.setAttStart(Timestamp.valueOf(attendanceDTOS.get(0).getAttTime()));
        }else{
          attendanceDaily.setAttStart(Timestamp.valueOf(attendanceDTOS.get(0).getAttTime()));
          attendanceDaily.setAttEnd(Timestamp.valueOf(attendanceDTOS.get(attendanceDTOS.size()-1).getAttTime()));
        }
        //计算工时
        Float attHour=attendanceService.calculationTime(employeeInfo.getCompanyInfo().getId(), DateHelper.stringToDateLong(attendanceDTOS.get(0).getAttTime(),"yyyy-MM-dd HH:mm:ss"),DateHelper.stringToDateLong(attendanceDTOS.get(attendanceDTOS.size()-1).getAttTime(),"yyyy-MM-dd HH:mm:ss"));
        attendanceDaily.setAttHours(attHour);
      }
      attendanceDaily.setWorkHours(8);
      if(nonNull(employeeInfo.getCompanyInfo())){
        Map<String,String> dateRange=attendanceService.getAttTime(timestamp,employeeInfo.getCompanyInfo().getId());
        String startTime=dateRange.get(ATT_RANGE_START);
        String endTime=dateRange.get(ATT_RANGE_END);
        if(nonNull(startTime)&&nonNull(endTime)){
          //计算加班时间
          float overTimeHours=attendanceApplyRepository.countHourPer(APPLY_OVERTIME,startTime,endTime,employeeInfo.getId());
          attendanceDaily.setOverTimeHours(overTimeHours);
          //计算请假时间
          float leaveHours=attendanceApplyRepository.countHourPer(APPLY_LEAVE,startTime,endTime,employeeInfo.getId());
          attendanceDaily.setLeaveHours(leaveHours);
        }
      }
      attendanceDaily.setEmployeeInfo(employeeInfo);
      attendanceDailies.add(attendanceDaily);
    }
    attendanceDailyRepository.saveAll(attendanceDailies);
  }

  *//**
   *每月定时
   *//*
  @Transactional
   @Scheduled(cron = "0 00 08 01 * ?") // 每月第一天08点执行
  public void monthSchedule() {
    //获取上个月时间（每月定时会在一个月结束后统计）
    Timestamp timestamp=new Timestamp(System.currentTimeMillis()-60*60*24*1000);
    //获取月份
    String attMonth=DateHelper.timeStampFormater(timestamp,"yyyy-MM");
    List<EmployeeInfo> employeeInfos=employeeInfoRepository.findByStatus(1);
    List<AttendanceMonth> attendanceMonths=new ArrayList<>();
    for(int i=0;i<employeeInfos.size();i++) {
      //月统计
      EmployeeInfo employeeInfo = employeeInfos.get(i);
      AttendanceMonth check = attendanceMonthRepository.findByEmployeeInfoIdAndAttMonth(employeeInfo.getId(),attMonth);
      if(nonNull(check)&&nonNull(employeeInfo.getCompanyInfo())){
        AttendanceMonth attendanceMonth=new AttendanceMonth();
        attendanceMonth.setEmployeeInfo(employeeInfo);
        attendanceMonth.setAttMonth(attMonth);
        Map<String,Timestamp> monthRangeDate=attendanceService.getMonthRangeDate(attMonth,employeeInfo.getCompanyInfo().getId());
        Timestamp startTime=monthRangeDate.get(MONTH_RANGE_START);
        Timestamp endTime=monthRangeDate.get(MONTH_RANGE_END);
        if(nonNull(startTime)&&nonNull(endTime)){
          List<Object[]> count=attendanceApplyRepository.countHourPerMonth(startTime,endTime,employeeInfo.getId());
          attendanceMonth.setAttHours(Float.valueOf(count.get(0)[0].toString()));
          attendanceMonth.setOverTimeHours(Float.valueOf(count.get(0)[1].toString()));
          attendanceMonth.setLeaveHours(Float.valueOf(count.get(0)[2].toString()));
          attendanceMonth.setWorkHours(Float.valueOf(count.get(0)[3].toString()));
        }
        attendanceMonths.add(attendanceMonth);
      }
    }
    attendanceMonthRepository.saveAll(attendanceMonths);
  }*/
}