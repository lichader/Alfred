package com.lichader.alfred.test.metro;

import com.lichader.alfred.metroapi.v3.MetroServiceURLBuilder;
import com.lichader.alfred.metroapi.v3.RouteTypeService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lichader on 11/5/17.
 */

public class MetroServiceURLBuilderTest {

    private MetroServiceURLBuilder subject;

    public MetroServiceURLBuilderTest(){
        subject = new MetroServiceURLBuilder();
        subject.setApiDeveloperId("9423423");
        subject.setApiKey("ajdlajflsadjflsadj");
    }

    @Test
    public void calculateSignature_expectCorrect(){
        try {
            String signature = subject.calculateSignature(RouteTypeService.RESOURCE_ROUTE_TYPES);
            Assert.assertEquals("AE1CB78FD7AA80885803B1B3CBFC126A53325972", signature);
        } catch (Exception ex){
            Assert.fail(ex.getMessage());
        }
    }
}
