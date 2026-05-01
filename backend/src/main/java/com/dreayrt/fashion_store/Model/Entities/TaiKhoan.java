package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.List;

@Entity
@Table(name = "TaiKhoan")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TaiKhoan {
    @Id
    @Column(name = "Username")
    private String username;

    @Column(name = "PasswordHash")
    private String password;

    @Column(name = "VaiTro")
    private String vaiTro;

    @Column(name = "Email")
    private String email;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "TrangThai")
    private String trangThai;

    @Column(name="Phone")
    private String phone;

    @Column(name ="avatar" )
    private String Avatar;

    @OneToOne(mappedBy = "taikhoan",fetch =  FetchType.LAZY)
    private ShoppingCart shoppingCart;

    @JsonIgnore
    @OneToMany(mappedBy ="taiKhoan" ,fetch =  FetchType.LAZY)
    private List<Order>orders ;


    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch =   FetchType.LAZY)
    private List<Conversation> conversationsUser;

    @JsonIgnore
    @OneToMany(mappedBy = "staff",fetch =  FetchType.LAZY)
    private List<Conversation> conversationsStaff;

    @JsonIgnore
    @OneToMany(mappedBy = "sender",fetch =  FetchType.LAZY)
    private List<Message> messages;





    public TaiKhoan() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Conversation> getConversationsUser() {
        return conversationsUser;
    }

    public void setConversationsUser(List<Conversation> conversationsUser) {
        this.conversationsUser = conversationsUser;
    }

    public List<Conversation> getConversationsStaff() {
        return conversationsStaff;
    }

    public void setConversationsStaff(List<Conversation> conversationsStaff) {
        this.conversationsStaff = conversationsStaff;
    }
}
