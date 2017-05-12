package com.lichader.test.alfred.metro;

import com.lichader.alfred.service.MetroService;
import com.lichader.alfred.service.model.RouteTypesResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lichader on 11/5/17.
 */
public class MetroServiceTest {

    private MetroService subject = new MetroService();

    @Test
    public void testCalculateSignature(){

        try {
            String signature = subject.calculateSignature(MetroService.API_VERSION,
                    MetroService.API_RESOURCE_ROUTE_TYPE);

            Assert.assertEquals("AF2BEA0811A8720B70959FF93F1CB296545AD358", signature);
        } catch (Exception ex){
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testGetRouteTypes(){
        try {
            RouteTypesResponse response = subject.getRouteTypes();

            Assert.assertNotNull(response.RouteTypes);
            Assert.assertEquals(5, response.RouteTypes.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
