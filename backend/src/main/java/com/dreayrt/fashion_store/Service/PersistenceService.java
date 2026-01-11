package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.Kho;
import com.dreayrt.fashion_store.repository.PersistenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceService {
    @Autowired
    PersistenceRepository persistenceRepository;
    public List<Kho> getSanPhamKhoList(){
        return persistenceRepository.findAll();
    }
}
