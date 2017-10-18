package com.lichader.alfred.metroapi.v3;

import com.lichader.alfred.metroapi.v3.model.RouteTypesResponse;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class RouteTypeService extends MetroService{

    public final static String RESOURCE_ROUTE_TYPES = "route_types";

    public Optional<RouteTypesResponse> getAll(){
        return getResource(RESOURCE_ROUTE_TYPES, RouteTypesResponse.class);
    }

}
