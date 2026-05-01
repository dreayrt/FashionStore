package com.dreayrt.fashion_store.Model.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "VisitLog")
public class VisitLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "accessTime")
    private Date accessTime;

    @Column(name = "placeVisit")
    private String placeVisit;

    public VisitLog() {}

    public VisitLog(String username, Date accessTime, String placeVisit) {
        this.username = username;
        this.accessTime = accessTime;
        this.placeVisit = placeVisit;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
    }

    public String getPlaceVisit() {
        return placeVisit;
    }

    public void setPlaceVisit(String placeVisit) {
        this.placeVisit = placeVisit;
    }
}