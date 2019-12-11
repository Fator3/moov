package com.fator3.moov.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.moov.models.TimedLatLng;

@RestController
@RequestMapping(path = "/moov/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/filter")
    public List<PersistentProperty> filter(@RequestBody final List<TimedLatLng> references) {
        List<PersistentProperty> x = propertyService.findWithinRange(references);
        System.out.println(x.size());
        return x;
    }

    @PostMapping("/time")
    public List<TimedLatLng> distanceOfReferences(@RequestBody final List<TimedLatLng> references) {
        return propertyService.findDistanceInSeconds(references);
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