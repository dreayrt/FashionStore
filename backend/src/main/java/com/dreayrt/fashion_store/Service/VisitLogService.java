package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.TaiKhoan;
import com.dreayrt.fashion_store.Model.Entities.VisitLog;
import com.dreayrt.fashion_store.repository.TaiKhoanRepository;
import com.dreayrt.fashion_store.repository.VisitLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class VisitLogService {

    @Autowired
    private VisitLogRepository visitLogRepository;

    @Transactional
    public void logVisit(String username, String place) {

        if ("anonymousUser".equals(username)) {
            username = null;
        }
        VisitLog log = new VisitLog(
                username,
                new Date(),
                place
        );

        visitLogRepository.save(log);
    }
}