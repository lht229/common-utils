package com.tianhui.zhishu;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.common.utils.CommonUtilsApp;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ContextConfiguration({"classpath:applicationContext.xml","application.properties"})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CommonUtilsApp.class)
public abstract class SpringBootTestCaseBase {

}
