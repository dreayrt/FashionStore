package com.dreayrt.fashion_store.DTOs;

import java.math.BigDecimal;

public class OrderPreviewDTO {
    private BigDecimal subtotal;
    private int totalQuantity;
    private BigDecimal shippingFee;
    private BigDecimal discount;
    private BigDecimal finalTotal;
    private String voucherCode;
    private boolean voucherValid;
    private String errorMessage;

    // Getters and Setters
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public int getTotalQuantity() { return totalQuantity; }
    public void setTotalQuantity(int totalQuantity) { this.totalQuantity = totalQuantity; }

    public BigDecimal getShippingFee() { return shippingFee; }
    public void setShippingFee(BigDecimal shippingFee) { this.shippingFee = shippingFee; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public BigDecimal getFinalTotal() { return finalTotal; }
    public void setFinalTotal(BigDecimal finalTotal) { this.finalTotal = finalTotal; }

    public String getVoucherCode() { return voucherCode; }
    public void setVoucherCode(String voucherCode) { this.voucherCode = voucherCode; }

    public boolean isVoucherValid() { return voucherValid; }
    public void setVoucherValid(boolean voucherValid) { this.voucherValid = voucherValid; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
