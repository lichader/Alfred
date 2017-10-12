package com.lichader.test.alfred.metro;

import com.lichader.alfred.metroapi.v3.MetroService;
import com.lichader.alfred.metroapi.v3.MetroServiceURLBuilder;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import com.lichader.test.alfred.IntegrationTest;
import com.lichader.test.alfred.IntegrationTestApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lichader on 11/5/17.
 */

public class MetroServiceTest {

    private MetroServiceURLBuilder subject;

    public MetroServiceTest(){
        subject = new MetroServiceURLBuilder();
        subject.setApiDeveloperId("9423423");
        subject.setApiKey("ajdlajflsadjflsadj");
    }

    @Test
    public void testCalculateSignature(){

        try {
            String signature = subject.calculateSignature(RouteTypeService.RESOURCE_ROUTE_TYPES);
            Assert.assertEquals("AE1CB78FD7AA80885803B1B3CBFC126A53325972", signature);
        } catch (Exception ex){
            Assert.fail(ex.getMessage());
        }
    }
}
