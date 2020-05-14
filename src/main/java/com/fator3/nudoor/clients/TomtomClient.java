package com.fator3.nudoor.clients;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fator3.nudoor.models.GeolocationResponse;
import com.fator3.nudoor.models.LatLng;
import com.fator3.nudoor.models.ReachableRangeResponse;
import com.fator3.nudoor.models.RouteResponse;
import com.fator3.nudoor.models.TimedLatLng;
import com.vividsolutions.jts.geom.Point;

@Component
public class TomtomClient {

    static final Logger logger = LoggerFactory.getLogger(TomtomClient.class);

    @Autowired
    private RestTemplate restTemplate;
    private static String[] API_KEYS;
    
    @Value("${fator3.nudoor.tomtom.keys}")
    private void setAPI_KEYS(String[] keys) {
    	API_KEYS = keys;
    }

    private static AtomicInteger currentKeyIndex = new AtomicInteger();
    private static AtomicInteger requestCount = new AtomicInteger();
    private static final int DAILY_REQUEST_MAX = 2500;
    
    private static String getAPIKey() {
    	logger.info("Tomtom Api: getting API KEY");
    	if(requestCount.get() == DAILY_REQUEST_MAX) {
    		logger.info("Tomtom Api: changing API KEY");
    		currentKeyIndex = new AtomicInteger(currentKeyIndex.incrementAndGet() % API_KEYS.length);
    		requestCount = new AtomicInteger();
    	}
    	requestCount.incrementAndGet();
    	logger.info("Tomtom Api: request "+requestCount.get() + " | index "+currentKeyIndex.get() + " | key "+ API_KEYS[currentKeyIndex.get()]);
    	return API_KEYS[currentKeyIndex.get()];
    }
    
    private static final String REACHABLE_RANGE_URL = "https://api.tomtom.com/routing/1/calculateReachableRange/";
    private static final String BASE_PARAMS = "/json?routeType=fastest";
    private static final String SECONDS_PARAM = "&timeBudgetInSec=";
    private static final String TRAFFIC_PARAM = "&traffic=";
    private static final String TRANSPORT_PARAM = "&travelMode=";
    
    private static final String KEY_PARAM = "&key=";

    private static final String ROUTE_URL = "https://api.tomtom.com/routing/1/calculateRoute/";

    private static final String GEOLOCATION_SPECIFIC_URL = "https://api.tomtom.com/search/2/structuredGeocode.json?";
    private static final String COUNTRY_CODE_PARAM = "countryCode=BR";
    private static final String STREET_NUMBER_PARAM = "&streetNumber=";
    private static final String MUNICIPALITY_PARAM = "&municipality=";
    private static final String COUNTRY_SUBDIVISION_PARAM = "&countrySubdivision=";
//    private static final String POSTAL_CODE_PARAM = "&postalCode=";

    private static final String GEOLOCATION_URL = "https://api.tomtom.com/search/2/search/";
    private static final String COUNTRY_SET_PARAM = ".json?limit=1&maxFuzzyLevel=2&countrySet=BR";

    public GeolocationResponse getLatLngSpecificApi(final String reference) {
        logger.info("Tomtom Api: getting route");

        final StringBuilder uri = new StringBuilder();
        uri.append(GEOLOCATION_SPECIFIC_URL);
        uri.append(COUNTRY_CODE_PARAM);
        uri.append(STREET_NUMBER_PARAM);
        uri.append("21");
        uri.append(MUNICIPALITY_PARAM);
        uri.append("Sao Paulo");
        uri.append(COUNTRY_SUBDIVISION_PARAM);
        uri.append("Sao Paulo");
//        uri.append(POSTAL_CODE_PARAM);
//        uri.append("05539020");
        uri.append(KEY_PARAM);
        uri.append(getAPIKey());

        final ResponseEntity<GeolocationResponse> result = restTemplate.getForEntity(uri.toString(),
                GeolocationResponse.class);
        logger.info("Tomtom Api: success request");

        return result.getBody();
    }

    public GeolocationResponse getLatLng(final String reference) {
        logger.info("Tomtom Api: getting route");

        final StringBuilder uri = new StringBuilder();
        uri.append(GEOLOCATION_URL);
        uri.append(reference);
        uri.append(COUNTRY_SET_PARAM);
        uri.append(KEY_PARAM);
        uri.append(getAPIKey());

        final ResponseEntity<GeolocationResponse> result = restTemplate.getForEntity(encodeURIComponent(uri.toString()),
                GeolocationResponse.class);
        logger.info("Tomtom Api: success request");

        return result.getBody();
    }

    public ReachableRangeResponse getPolygonReachable(final Point location, final Integer minutes,
            final boolean traffic, final String transport) {
        logger.info("Tomtom Api: getting reachable range");

        final StringBuilder uri = new StringBuilder();
        uri.append(REACHABLE_RANGE_URL);
        uri.append(LatLng.of(location).toUrlString());
        uri.append(BASE_PARAMS);
        uri.append(SECONDS_PARAM);
        uri.append(minutes * 60);
        uri.append(TRAFFIC_PARAM);
        uri.append(traffic);
        uri.append(TRANSPORT_PARAM);
        uri.append(transport);
        uri.append(KEY_PARAM);
        uri.append(getAPIKey());

        final ResponseEntity<ReachableRangeResponse> result = restTemplate
                .getForEntity(uri.toString(), ReachableRangeResponse.class);

        logger.info("Tomtom Api: success request");
        return result.getBody();
    }

    public ReachableRangeResponse getPolygonReachable(final Point location, final Integer minutes, final String transport) {
        return getPolygonReachable(location, minutes, true, transport);
    }

    public RouteResponse getRoute(final List<TimedLatLng> locations, final String transport) {
        logger.info("Tomtom Api: getting route");
        final boolean traffic = true;

        final StringBuilder uri = new StringBuilder();
        uri.append(ROUTE_URL);
        for (TimedLatLng location : locations) {
            uri.append(location.toUrlString() + ":");
        }
        uri.deleteCharAt(uri.length() - 1);
        uri.append(BASE_PARAMS);
        uri.append(TRAFFIC_PARAM);
        uri.append(traffic);
        uri.append(TRANSPORT_PARAM);
        uri.append(transport);
        uri.append(KEY_PARAM);
        uri.append(getAPIKey());

        final ResponseEntity<RouteResponse> result = restTemplate.getForEntity(encodeURIComponent(uri.toString()),
                RouteResponse.class);
        logger.info("Tomtom Api: success request");

        return result.getBody();
    }

    public static String encodeURIComponent(String s) {
        String result;

            result = s
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        

        return result;
    }
}
