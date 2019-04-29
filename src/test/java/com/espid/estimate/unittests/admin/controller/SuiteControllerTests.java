package com.espid.estimate.unittests.admin.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses (
    value = {
          SenderControllerTests.class
        , VenderControllerTests.class
        , ToolControllerTests.class
        , PriceControllerTests.class
    }
)
public class SuiteControllerTests {

}