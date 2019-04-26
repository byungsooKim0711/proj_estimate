package com.espid.estimate.unittests.admin.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.espid.estimate.admin.estimate.mapper.EstimateMapper;
import com.espid.estimate.admin.estimate.model.Estimate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@MybatisTest
public class EstimateRepositoryTests {

    @Autowired
    private EstimateMapper estimateMapper;

    @Test
    public void shouldFindNoEstimatesIfrepositoryIsEmpty() throws Exception {
        /* arrange */
        String search = null;
        String from = "2018-01-01";
        String to = "2019-04-26";

        /* act */
        List<Estimate> estimates = estimateMapper.selectEstimate(from, to, search);

        assertThat(estimates).isEmpty();
    }

    @Test
    public void test() throws Exception {
    
    }
}