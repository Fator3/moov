package com.fator3.nudoor.property;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fator3.nudoor.models.RoutePost;
import com.fator3.nudoor.models.SearchParamsDTO;
import com.fator3.nudoor.models.TimedLatLng;
import com.google.common.collect.Lists;

@RestController
@RequestMapping(path = "/nudoor/property")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @PostMapping("/filter")
    public List<Property> filter(@RequestBody final SearchParamsDTO searchParams) {
    	
        return propertyService.findWithinRange(searchParams);
    }

    @PostMapping("/time")
    public List<TimedLatLng> distanceOfReferences(@RequestBody final RoutePost routePost) throws InterruptedException {
        return propertyService.findDistanceInSeconds(Lists.newArrayList(routePost.getProperty()),
                routePost.getAddress());
    }

    @PostMapping("/all")
    public List<Property> distanceOfReferences() {
        return propertyService.findAll();
    }
    
    @GetMapping("/random/{limit}")
    public List<Property> listNRandom(@PathVariable("limit") Integer limit){
    	return propertyService.listNRandom(limit);
    }
}