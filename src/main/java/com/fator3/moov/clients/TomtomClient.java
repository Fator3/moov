package com.fator3.moov.clients;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fator3.moov.models.LatLng;
import com.fator3.moov.models.ReachableRangeResponse;
import com.fator3.moov.models.RouteResponse;
import com.fator3.moov.models.TimedLatLng;
import com.vividsolutions.jts.geom.Point;

@Component
public class TomtomClient {

    final Logger logger = LoggerFactory.getLogger(TomtomClient.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${fator3.moov.tomtom.key}")
    private String API_KEY;

    private static final String REACHABLE_RANGE_URL = "https://api.tomtom.com/routing/1/calculateReachableRange/";
    private static final String BASE_PARAMS = "/json?routeType=fastest&travelMode=car&vehicleMaxSpeed=100&vehicleCommercial=false";
    private static final String SECONDS_PARAM = "&timeBudgetInSec=";
    private static final String TRAFFIC_PARAM = "&traffic=";
    private static final String KEY_PARAM = "&key=";

    private static final String ROUTE_URL = "https://api.tomtom.com/routing/1/calculateRoute/";

    public ReachableRangeResponse getPolygonReachable(final Point location, final Integer minutes,
            final boolean traffic) {
        logger.info("Tomtom Api: getting reachable range");

        final StringBuilder uri = new StringBuilder();
        uri.append(REACHABLE_RANGE_URL);
        uri.append(LatLng.of(location).toUrlString());
        uri.append(BASE_PARAMS);
        uri.append(SECONDS_PARAM);
        uri.append(minutes * 60);
        uri.append(TRAFFIC_PARAM);
        uri.append(traffic);
        uri.append(KEY_PARAM);
        uri.append(API_KEY);

        final ResponseEntity<ReachableRangeResponse> result = restTemplate
                .getForEntity(uri.toString(), ReachableRangeResponse.class);

        logger.info("Tomtom Api: success request");
        return result.getBody();
    }

    public ReachableRangeResponse getPolygonReachable(final Point location) {
        return getPolygonReachable(location, 15, false);
    }

    public RouteResponse getRoute(final List<TimedLatLng> locations) {
        logger.info("Tomtom Api: getting route");
        final boolean traffic = false;

        final StringBuilder uri = new StringBuilder();
        uri.append(ROUTE_URL);
        for (TimedLatLng location : locations) {
            uri.append(location.toUrlString() + ":");
        }
        uri.deleteCharAt(uri.length() - 1);
        uri.append(BASE_PARAMS);
        uri.append(TRAFFIC_PARAM);
        uri.append(traffic);
        uri.append(KEY_PARAM);
        uri.append(API_KEY);
        
        final ResponseEntity<RouteResponse> result = restTemplate.getForEntity(uri.toString(),
                RouteResponse.class);
        logger.info("Tomtom Api: success request");

        return result.getBody();
    }

//    private String getApiKeyUrl() {
//        return "?hapikey=" + API_KEY;
//    }

}
