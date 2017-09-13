package com.tianhui.zhishu.ValidatorCase;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.utils.utils.common.annotation.ValidatorService;
import com.tianhui.zhishu.SpringBootTestCaseBase;

public class ValidatorTestCase extends SpringBootTestCaseBase {

    @Autowired
    private ValidatorService validatorService;


    @Test
    public void testJsonUtil() throws Exception {
        DeploymentDto dto = new DeploymentDto();

        dto.setProjectId(1);
        dto.setShopIds("1");
        dto.setFrom(1);
        validatorService.valid(dto);

    }
}