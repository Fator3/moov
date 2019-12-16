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

//    @PostMapping("/filter")
//    public List<PersistentProperty> filter(@RequestBody final List<TimedLatLng> references) {
//        List<PersistentProperty> x = propertyService.findWithinRange(references);
//        System.out.println(x.size());
//        return x;
//    }

    @PostMapping("/filter")
    public List<PersistentProperty> filter2(@RequestBody final List<String> references) {
        List<PersistentProperty> x = propertyService.findWithinRange2(references);
        return x;
    }

//    @PostMapping("/time")
//    public List<TimedLatLng> distanceOfReferences(@RequestBody final List<TimedLatLng> references) {
//        return propertyService.findDistanceInSeconds(references);
//    }

    @PostMapping("/time")
    public List<TimedLatLng> distanceOfReferences2(@RequestBody final RoutePost routePost) {
        return propertyService.findDistanceInSeconds2(Lists.newArrayList(routePost.getProperty()),
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