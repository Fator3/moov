package com.fator3.moov.property;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vividsolutions.jts.geom.Point;

public interface PropertyRepository extends JpaRepository<PersistentProperty, Integer> {

    @Query("select p from property p where within (:point, p.reachableRange) = true")
    List<PersistentProperty> findWithin(@Param("point") Point point);
    
    List<PersistentProperty> findAll();

}
