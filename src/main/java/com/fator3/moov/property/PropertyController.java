package com.fator3.moov.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.moov.models.RoutePost;
import com.fator3.moov.models.TimedLatLng;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(path = "/moov/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/filter")
    public List<PersistentProperty> filter(@RequestBody final List<String> references) {
        return propertyService.findWithinRange(references);
    }

    @PostMapping("/time")
    public List<TimedLatLng> distanceOfReferences(@RequestBody final RoutePost routePost) {
        return propertyService.findDistanceInSeconds(Lists.newArrayList(routePost.getProperty()),
                routePost.getAddress());
    }

    @PostMapping("/all")
    public List<PersistentProperty> distanceOfReferences() {
        return propertyService.findAll();
    }

    @PostMapping("/test")
    public void createPolygon() {
        propertyService.saveTestPolygon();
    }

}