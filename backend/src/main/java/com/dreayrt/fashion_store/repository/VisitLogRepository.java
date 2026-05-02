package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.VisitLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    List<VisitLog> findTop10ByOrderByAccessTimeDesc();

    Page<VisitLog> findAll(Pageable pageable);

    Page<VisitLog> findByUsernameContainingIgnoreCaseOrPlaceVisitContainingIgnoreCase(String username, String placeVisit, Pageable pageable);
}
