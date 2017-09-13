package com.common.utils.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.common.utils.controller.validate.BussinessLog;
import com.common.utils.enity.ProjectSurveyRewardDto;
import com.common.utils.enity.User;
import com.common.utils.utils.common.ResponseCode;
import com.common.utils.utils.common.exception.MetaRestResponse;

@RestController
@RequestMapping("/api/v1/thzs")
public class CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    /**
     * 获取项目对象.
     */
    @RequestMapping(value = {"/test"}, method = {RequestMethod.GET})
    public MetaRestResponse findProject() {
        LOGGER.info("---------");
        Map<String, String> resultMap = new HashMap<String, String>();
        String indx = "index";
        indx.indexOf("a");

        resultMap.put("test", "tttt");
        return MetaRestResponse.success(ResponseCode.SUCCESS, resultMap);
    }

    @PostMapping(value = {"/update"})
    public MetaRestResponse update(@Validated @RequestBody ProjectSurveyRewardDto projectSurveyRewardDto) {
        LOGGER.info("--***-------");
        return MetaRestResponse.success(ResponseCode.SUCCESS, projectSurveyRewardDto);
    }

    @PostMapping(value = {"/userInfo"})
    @BussinessLog(value = "用户信息", key = "userInfoss", dict = "user")
    public MetaRestResponse userInfo(@Validated @RequestBody User user) {
        LOGGER.info("--***-------");
        return MetaRestResponse.success(ResponseCode.SUCCESS, user);
    }

}
