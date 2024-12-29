package org.example.autobase.repository;

import org.example.autobase.entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByIsTaken(boolean isTaken);
}
