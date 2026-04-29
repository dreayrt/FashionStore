package com.dreayrt.fashion_store.Service;

import com.dreayrt.fashion_store.Model.Entities.Order;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SePayService {

    @Value("${sepay.bank:}")
    private String bank;

    @Value("${sepay.account:}")
    private String account;

    public String createPaymentUrl(Order order) {
        String amount = String.valueOf(order.getTongTien().longValue());
        String orderId = "DRY" + order.getMaDonHang();
        
        // SePay VietQR format: https://qr.sepay.vn/img?acc={account}&bank={bank}&amount={amount}&des={description}
        return "https://qr.sepay.vn/img?acc=" + account +
               "&bank=" + bank +
               "&amount=" + amount +
               "&des=" + orderId;
    }
}
