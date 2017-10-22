package com.lichader.test.alfred;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest(classes = com.lichader.alfred.Application.class)
@ActiveProfiles("test")
public abstract  class AbstractSpringBootTestBase {
}
