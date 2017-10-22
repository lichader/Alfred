package com.lichader.alfred.metroapi.v3;

import com.lichader.alfred.metroapi.v3.model.DisruptionsResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DisruptionService extends MetroService{

   public final static String RESOURCE_ALL_DISRUPTIONS = "disruptions";
   public final static String RESOURCE_SPECIFIC_ROUTE_DISRUP = "disruptions/route/";


    public Optional<DisruptionsResponse> getAll() {
        return getResource(RESOURCE_ALL_DISRUPTIONS, DisruptionsResponse.class);

    }


    public Optional<DisruptionsResponse> getDisruption(int routeId){
        String resource = RESOURCE_SPECIFIC_ROUTE_DISRUP + routeId;
        return getResource(resource, DisruptionsResponse.class);
    }


}
