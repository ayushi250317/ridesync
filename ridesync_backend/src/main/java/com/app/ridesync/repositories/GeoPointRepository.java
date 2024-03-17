package com.app.ridesync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.ridesync.entities.GeoPoint;

@Repository
public interface GeoPointRepository extends JpaRepository<GeoPoint,Long>{}
