package com.gzzhsl.pcms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gzzhsl.pcms.entity.ReservoirCode;
import com.gzzhsl.pcms.enums.StationTypeEnum;
import com.gzzhsl.pcms.enums.SysEnum;
import com.gzzhsl.pcms.exception.SysException;
import com.gzzhsl.pcms.service.ReservoirCodeService;
import com.gzzhsl.pcms.util.ResultUtil;
import com.gzzhsl.pcms.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@Controller
@RequestMapping("/communication")
@Slf4j
@RequiresRoles("manager")
public class CommunicationController {
    private static final String RAINFALLSERVERURL = "http://42.123.116.200:8083/com.zhsl.waterrain.service.RainService";
    private static final String WATERLEVELSERVERURL = "http://42.123.116.200:8083/com.zhsl.waterrain.service.RiverService";
    @Autowired
    private ReservoirCodeService reservoirCodeService;




    /**
     * 获取某水库检测站点
     * @param plantName
     * @return
     */
    @GetMapping("/getstations")
    @ResponseBody
    public ResultVO getStations(String plantName) {
        List<StationVO> stationVOs = getMyStaionVO(plantName);
        if (stationVOs == null || stationVOs.size()==0) {
            return ResultUtil.failed(SysEnum.FAIL_TO_FETCH_STATION_INFO_ERROR);
        }
        return ResultUtil.success(stationVOs);
    }

    /**
     *
     * 获取某一水库的所有测站的雨量信息
     * @param stationId
     * @return
     */
    @GetMapping("/getallrainfalldata")
    @ResponseBody
    public ResultVO getAllRainfallData(String baseInfoId, String startTime, String endTime) {
        List<StationVO> stationVOs = getMyStaionVO(baseInfoId); // 获取所有的监测站点
        if (stationVOs == null || stationVOs.size()==0) {
            log.error("【雨量信息】 获取监测站点失败！ stationVOs={}", stationVOs);
            throw new SysException(SysEnum.FAIL_TO_FETCH_MONITOR_STATION_ERROR);
        }
        List<StationVO> rainfallMonitorStations = new ArrayList<>();
        for (StationVO stationVO : stationVOs) {
            if (stationVO.getStationType() == StationTypeEnum.RAINFALL.getCode()) { // 凡是雨量监测站 全部加到rainfallMonitorStations
                rainfallMonitorStations.add(stationVO);
            }
        }
        if (rainfallMonitorStations == null || rainfallMonitorStations.size()==0) {
            return ResultUtil.failed(SysEnum.FAIL_TO_FETCH_RAINFALL_ERROR);
        }

        /*Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String startTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time); // 当前时间的前一天

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 当前时间
        String endTimeStr = formatter.format(currentTime);*/

        List<StationVOWithMonitorData> stationVOWithMonitorDatas = getAllRainFallData(rainfallMonitorStations, startTime, endTime);
        return ResultUtil.success(stationVOWithMonitorDatas);
    }

    /**
     *
     * 获取某一水库的所有测站的雨量信息
     * @param stationId
     * @return
     */
    @GetMapping("/getallwaterleveldata")
    @ResponseBody
    public ResultVO getAllWaterLevelData(String baseInfoId, String startTime, String endTime) {
        List<StationVO> stationVOs = getMyStaionVO(baseInfoId); // 获取所有的监测站点
        if (stationVOs == null || stationVOs.size()==0) {
            log.error("【水位信息】 获取监测站点失败！ stationVOs={}", stationVOs);
            throw new SysException(SysEnum.FAIL_TO_FETCH_MONITOR_STATION_ERROR);
        }
        List<StationVO> waterlevelMonitorStations = new ArrayList<>();
        for (StationVO stationVO : stationVOs) {
            if (stationVO.getStationType() == StationTypeEnum.WATER_LEVEL.getCode()) { // 凡是雨量监测站 全部加到rainfallMonitorStations
                waterlevelMonitorStations.add(stationVO);
            }
        }
        if (waterlevelMonitorStations == null || waterlevelMonitorStations.size()==0) {
            return ResultUtil.failed(SysEnum.FAIL_TO_FETCH_WATERLEVEL_ERROR);
        }

        /*Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date time=cal.getTime();
        String startTimeStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time); // 当前时间的前一天

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 当前时间
        String endTimeStr = formatter.format(currentTime);*/

        List<StationVOWithMonitorData> stationVOWithMonitorDatas = getAllWaterLevelData(waterlevelMonitorStations, startTime, endTime);
        return ResultUtil.success(stationVOWithMonitorDatas);
    }






    /**
     * 根据水库编码获取水库水雨情测站编码(服务器编码)
     * 这里是去雨量服务地址获取所有类型站点列表 服务器端没有做分类
     * @param proId 水库编码
     * @return
     */
    public List<StationVO> getStationVOs(List<String> proIds) {
        if (proIds == null || proIds.size() == 0) {
            log.error("【监测站点】 水库大坝ID为空！");
            return null;
        }
        List<StationVO> stationVOs = new ArrayList();
        String method = "queryMeasuringStationSer"; // 根据水库编码获取水库水雨情测站编码(服务器编码)
        // 返回：stationId测站编码 stationType站点类型：0雨量水位 1雨量 2水位 name测站名称 lgtd经度 lttd纬度
        String rtStr = connect2Service(RAINFALLSERVERURL, proIds, method);
        ObjectMapper objectMapper = new ObjectMapper();
        if ("".equals(rtStr) || rtStr == null) {
            log.error("【监测站点】 获取监测站点出错!");
            return null;
        }
        try {
            StationsRtVO stationsRtVO = objectMapper.readValue(rtStr, StationsRtVO.class);
            stationVOs = stationsRtVO.getData();
        } catch (IOException e) {
            log.error("【监测站点】 监测站点信息转换出错 rtStr={}", rtStr);
            return null;
        }
        return stationVOs;
    }

    /**
     * 判断水库是否在本系统中存在，若存在返回其监测站点
     * @param plantName
     * @return
     */
    public List<StationVO> getMyStaionVO(String baseInfoId) {
        List<String> proIds = new ArrayList<>();
        ReservoirCode reservoirCode = reservoirCodeService.getByBaseInfoId(baseInfoId);
        if (reservoirCode == null) {
            log.error("【监测站点】系统不存在查询的水库名称， reservoirCode={}", reservoirCode);
            ResultUtil.failed(SysEnum.PLANTNAME_NO_EXIST_ERROR);
        }
        proIds.add(reservoirCode.getReservoirCode());
        List<StationVO> stationVOs = getStationVOs(proIds);
        if (stationVOs == null || stationVOs.size() == 0) {
            log.error("【监测站点】获取检测站点出错， stationVOs={}", stationVOs);
            ResultUtil.failed(SysEnum.FAIL_TO_FETCH_STATION_INFO_ERROR);
        }
        return stationVOs;
    }
    /**
     *
     * @param strURL 服务地址
     * @param arguments 水库编码集
     * @param method 调用方法名
     * @return 该水库所有测站信息的JSON字符串
     */
    public String connect2Service(String strURL, List<String> arguments, String method) {
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("arguments", arguments);
        params.put("method", method);
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(params);
            out.append(jsonString);
            out.flush();
            out.close();

            int code = connection.getResponseCode();
            BufferedReader reader;
            String allTempStr="";
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                String line;
                while ((line = reader.readLine()) != null){
                    allTempStr+=line;
                }
                reader.close();
            } else {
                return "请求网络状态错误";
            }
            return allTempStr;
        } catch (Exception e) {
            log.error("Exception occur when send http post request!", e);
        }
        return "error";
    }

    /**
     * 获取到特定水库的所有站点某一时段的所有雨量信息
     * @param stationVOs
     * @return
     */
    public List<StationVOWithMonitorData> getAllRainFallData(List<StationVO> stationVOs, String startTimeStr, String endTimeStr) {
        List<StationVOWithMonitorData> stationVOWithMonitorDatas = new ArrayList<StationVOWithMonitorData>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (stationVOs.size() == 0 || stationVOs == null) {
            log.error("【雨量信息】获取检测站点出错， stationVOs={}", stationVOs);
            ResultUtil.failed(SysEnum.FAIL_TO_FETCH_RAINFALL_ERROR);
        }
        CountDownLatch countDownLatch = new CountDownLatch(stationVOs.size());
        for (int i=0; i<stationVOs.size(); i++) {
            StationVOWithMonitorData stationVOWithMonitorData = new StationVOWithMonitorData();
            BeanUtils.copyProperties(stationVOs.get(i), stationVOWithMonitorData);
            List<String> arguments = new ArrayList<>();
            arguments.add(stationVOs.get(i).getStationId());
            arguments.add(startTimeStr);
            arguments.add(endTimeStr);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String rtStr = connect2Service(RAINFALLSERVERURL, arguments, "getRainProcess");
                        RainfallsRtVO rainfallsRtVO = objectMapper.readValue(rtStr, RainfallsRtVO.class);
                        stationVOWithMonitorData.setData(rainfallsRtVO.getData());
                        stationVOWithMonitorDatas.add(stationVOWithMonitorData);
                    } catch (Throwable e) {
                        // whatever
                    } finally {
                        // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        try {
            // 个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
            countDownLatch.await();
        } catch (InterruptedException e) {
           log.error("【雨量信息】 多线程获取雨量信息出错 e={}", e);
           return null;
        }
        return stationVOWithMonitorDatas;
    }

    /**
     * 获取到特定水库的所有站点某一时段的所有水位信息
     * @param stationVOs
     * @return
     */
    public  List<StationVOWithMonitorData> getAllWaterLevelData(List<StationVO> stationVOs, String startTimeStr, String endTimeStr) {
        List<StationVOWithMonitorData> stationVOWithMonitorDatas = new ArrayList<StationVOWithMonitorData>();
        ObjectMapper objectMapper = new ObjectMapper();
        if (stationVOs.size() == 0 || stationVOs == null) {
            log.error("【水位信息】获取检测站点出错， stationVOs={}", stationVOs);
            ResultUtil.failed(SysEnum.FAIL_TO_FETCH_WATERLEVEL_ERROR);
        }
        CountDownLatch countDownLatch = new CountDownLatch(stationVOs.size());
        for (int i=0; i<stationVOs.size(); i++) {
            StationVOWithMonitorData stationVOWithMonitorData = new StationVOWithMonitorData();
            BeanUtils.copyProperties(stationVOs.get(i), stationVOWithMonitorData);
            List<String> arguments = new ArrayList<>();
            arguments.add(stationVOs.get(i).getStationId());
            arguments.add(startTimeStr);
            arguments.add(endTimeStr);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String rtStr = connect2Service(WATERLEVELSERVERURL, arguments, "getRiverProcess");
                        WaterLevelsRtVO waterLevelsRtVO = objectMapper.readValue(rtStr, WaterLevelsRtVO.class);
                        stationVOWithMonitorData.setData(waterLevelsRtVO.getData());
                        stationVOWithMonitorDatas.add(stationVOWithMonitorData);
                    } catch (Throwable e) {
                        // whatever
                    } finally {
                        // 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        try {
            // 个线程countDown()都执行之后才会释放当前线程,程序才能继续往后执行
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("【雨量信息】 多线程获取雨量信息出错 e={}", e);
            return null;
        }
        return stationVOWithMonitorDatas;
    }

    public static void main(String[] args) {
  /*      CommunicationController c = new CommunicationController();
        List<String> params = new ArrayList<>();
        params.add("10000003");
        params.add("2018-08-09 05:06:56");
        params.add("2018-08-10 17:06:56");
        String temp = c.connect2Service("http://42.123.116.200:8083/com.zhsl.waterrain.service.RiverService", params, "getRiverProcess");
        System.out.println(temp);*/
  /*      CommunicationController c = new CommunicationController();
        List<String> params = new ArrayList<>();
        params.add("10000008");
        params.add("2018-08-08 00:00:00");
        params.add("2018-08-09 00:00:00");
        String temp = c.connect2Service("http://42.123.116.200:8083/com.zhsl.waterrain.service.RainService", params, "getRainProcess");
        System.out.println(temp);*/
    }
}
