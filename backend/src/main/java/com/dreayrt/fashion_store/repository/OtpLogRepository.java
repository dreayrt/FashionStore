package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.OtpLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface OtpLogRepository extends JpaRepository<OtpLog, Integer> {
    Optional<OtpLog> findFirstByEmailAndOtpAndExpiratedatAfterOrderByCreateatDesc(String email,
                                                                                  String otp,
                                                                                  Date now);
}
