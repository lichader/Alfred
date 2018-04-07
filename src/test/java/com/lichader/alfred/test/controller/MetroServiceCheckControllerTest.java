package com.lichader.alfred.test.controller;

import com.lichader.alfred.controller.tracking.TrackingServiceController;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingServiceController.class)
@ActiveProfiles("test")
public class MetroServiceCheckControllerTest {
}
