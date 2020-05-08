package com.fator3.nudoor.property;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findAll();
    
    @Query(value="select * from property order by rand() limit :limit", nativeQuery = true)
    List<Property> listNRandom(@Param("limit") int limit);

}
