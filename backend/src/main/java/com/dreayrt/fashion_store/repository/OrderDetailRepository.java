package com.dreayrt.fashion_store.repository;

import com.dreayrt.fashion_store.Model.Entities.Order;
import com.dreayrt.fashion_store.Model.Entities.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface OrderDetailRepository extends CrudRepository<OrderDetail,Integer> {
    List<OrderDetail> findByOrder(Order order);
}
