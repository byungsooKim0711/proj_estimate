package com.espid.estimate.unittests.admin.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses (
    value = {
          SenderRepositoryTests.class
        , VenderRepositoryTests.class
        , ToolRepositoryTests.class
        , PriceRepositoryTests.class
    }
)
public class SuiteRepositoryTests {

}