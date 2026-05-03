package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement,Integer> {
    @Query("SELECT q FROM Advertisement q WHERE q.ngayKetThuc >= CURRENT_TIMESTAMP AND q.ngayBatDau <= CURRENT_TIMESTAMP")
    List<Advertisement> findActiveAds();
}
