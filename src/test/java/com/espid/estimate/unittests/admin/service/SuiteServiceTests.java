package com.espid.estimate.unittests.admin.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses (
    value = {
            SenderServiceTests.class
          , VenderServiceTests.class
          , ToolServiceTests.class
          , PriceServiceTests.class
    }
)
public class SuiteServiceTests {

}