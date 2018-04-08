package com.lichader.alfred.test.controller;

import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@ComponentScan(basePackages = "come.lichader.alfred")
public abstract class AbstractControllerTest {
}
