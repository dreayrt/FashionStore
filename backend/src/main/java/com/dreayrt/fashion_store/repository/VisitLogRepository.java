package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.VisitLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitLogRepository extends JpaRepository<VisitLog, Long> {
    List<VisitLog> findTop10ByOrderByAccessTimeDesc();
}
