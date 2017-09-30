package com.lichader.test.alfred.metro;

import com.lichader.alfred.metroapi.v3.MetroService;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IntegrationTestApplication.class)
public class MetroServiceTest {

    @Autowired
    MetroService metroService;

    @Test
    public void testCalculateSignature(){

        try {
            String signature = metroService.calculateSignature(MetroService.API_VERSION,
                    MetroService.RESOURCE_ROUTE_TYPES);

            Assert.assertEquals("AF2BEA0811A8720B70959FF93F1CB296545AD358", signature);
        } catch (Exception ex){
            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testGetRouteTypes(){
        try {
            RouteTypesResponse response = metroService.getRouteTypes();

            Assert.assertNotNull(response.RouteTypes);
            Assert.assertEquals(5, response.RouteTypes.size());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetRoutes(){
        try {
            RouteResponse response = metroService.getRoutes();

            Assert.assertTrue(!response.Routes.isEmpty());

        } catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetDisruptions(){
        try {
            DisruptionsResponse response = metroService.getAllDisruptions();

            Assert.assertTrue(!response.disruptions.MetroTrain.isEmpty());
        } catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testGetAllDisruptionsOfARoute(){
        try {
            DisruptionsResponse respone = metroService.getDisruption(8); //hurstbridge line
            Assert.assertTrue(!respone.disruptions.MetroTrain.isEmpty());
        } catch (Exception e){
            Assert.fail(e.getMessage());
        }
    }
}
