package com.lichader.alfred.metroapi.v3;

import com.lichader.alfred.metroapi.v3.model.RouteResponse;
import com.lichader.alfred.servant.MetroAlfred;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RouteService extends MetroService{

    private final Logger logger = LoggerFactory.getLogger(MetroAlfred.class);

    public final static String RESOURCE_ROUTES = "routes";

    public Optional<RouteResponse> getAll(){
        return getResource(RESOURCE_ROUTES, RouteResponse.class);
    }
}
