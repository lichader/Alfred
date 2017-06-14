package com.lichader.test.alfred.metro;

import com.lichader.alfred.metroapi.v3.MetroService;
import com.lichader.alfred.metroapi.v3.model.Disruption;
import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import com.lichader.alfred.metroapi.v3.model.RouteResponse;
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
                    MetroService.RESOURCE_ROUTE_TYPES);

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

    @Test
    public void testGetRoutes(){
        try {
            RouteResponse response = subject.getRoutes();

            Assert.assertTrue(!response.Routes.isEmpty());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDisruptions(){
        try {
            DisruptionsResponse response = subject.getAllDisruptions();

            Assert.assertTrue(!response.disruptions.MetroTrain.isEmpty());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetAllDisruptionsOfARoute(){
        try {
            DisruptionsResponse respone = subject.getDisruption(8); //hurstbridge line
            Assert.assertTrue(!respone.disruptions.MetroTrain.isEmpty());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
