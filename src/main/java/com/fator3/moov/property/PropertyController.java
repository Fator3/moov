package com.fator3.moov.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.moov.models.RoutePost;
import com.fator3.moov.models.SearchParamsDTO;
import com.fator3.moov.models.TimedLatLng;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(path = "/moov/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/filter")
    public List<PersistentProperty> filter(@RequestBody final SearchParamsDTO searchParams) {
    	
        return propertyService.findWithinRange(searchParams);
    }

    @PostMapping("/time")
    public List<TimedLatLng> distanceOfReferences(@RequestBody final RoutePost routePost) throws InterruptedException {
        return propertyService.findDistanceInSeconds(Lists.newArrayList(routePost.getProperty()),
                routePost.getAddress());
    }

    @PostMapping("/all")
    public List<PersistentProperty> distanceOfReferences() {
        return propertyService.findAll();
    }

    @PostMapping("/test/{lat}/{lng}/{bairro}/{street}")
    public void createPolygon(@PathVariable double lat, @PathVariable double lng,
            @PathVariable String bairro, @PathVariable String street) {
        propertyService.saveTestPolygon(lat, lng, bairro, street);
    }

}