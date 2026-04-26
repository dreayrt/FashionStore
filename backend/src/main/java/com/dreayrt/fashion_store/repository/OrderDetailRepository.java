package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.OrderDetail;
import org.springframework.data.repository.CrudRepository;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer> {
}
