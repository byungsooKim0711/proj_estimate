package com.espid.estimate;

import com.espid.estimate.unittests.admin.controller.SuiteControllerTests;
import com.espid.estimate.unittests.admin.repository.SuiteRepositoryTests;
import com.espid.estimate.unittests.admin.service.SuiteServiceTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses (
    value = {
          SuiteRepositoryTests.class
        , SuiteServiceTests.class
        , SuiteControllerTests.class
    }
)
public class SuiteTests {

}