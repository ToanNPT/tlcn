package com.myproject.onlinecourses.repositories;

import com.myproject.onlinecourses.dto.RevenuesByMonth;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesByInYear;
import com.myproject.onlinecourses.repository.StatisticsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StatictisRevenuesTest {

    @Autowired
    StatisticsRepository repository;

//    @Before
//    @Autowired
//    public void init(StatisticsRepository repository){
//        this.repository = repository;
//    }

    @Test
    public void getRevenuesByMonthsTest(){
        List<IRevenuesByInYear> list = repository.getRevenuesByMonths("2022");
        list.stream().forEach( p  -> {
            System.out.println("month " + p.getMonth() + "--" + p.getValue());
        });
        Assertions.assertSame(3, list.size());
    }
}
