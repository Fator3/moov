package com.fator3.moov.configuration.geo;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.Geometry;

@Configuration
public class GeoJsonModule extends SimpleModule {

	private static final long serialVersionUID = -1235729863661288261L;

	public GeoJsonModule() {
		super("GeoJson", new Version(1, 0, 0, null, "com.bedatadriven", "jackson-geojson"));

		addSerializer(Geometry.class, new GeometrySerializer());
		addDeserializer(Geometry.class, new GeometryDeserializer());
	}
}