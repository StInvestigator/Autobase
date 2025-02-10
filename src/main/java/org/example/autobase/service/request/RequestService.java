package org.example.autobase.service.request;

import org.example.autobase.entity.Request;

import java.util.List;

public interface RequestService {
    void save(Request request);
    void saveAll(List<Request> requests);
    List<Request> findAll();
    List<Request> findAllNotTaken();
    void deleteAll();
    List<Request> getNextDayRandomRequests();
    Request findById(Long id);
}
