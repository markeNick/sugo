package com.sugo.wx;

import com.sugo.sql.entity.SugoCategory;
import com.sugo.sql.service.other.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;

import java.util.List;

@SpringBootTest
public class Test1 {

    @Autowired
    private CategoryService categoryService;


    @Test
    public void test() {
        List<SugoCategory> list = categoryService.queryL1();
        for (SugoCategory s : list) {
            System.out.println(s.toString());
        }
    }
}