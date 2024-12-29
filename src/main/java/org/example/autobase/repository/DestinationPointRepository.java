package org.example.autobase.repository;

import org.example.autobase.entity.DestinationPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DestinationPointRepository extends JpaRepository<DestinationPoint, Long> {
}
