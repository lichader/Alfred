package com.lichader.alfred.db.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name="modified_date", nullable = false)
    private ZonedDateTime lastModifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @PrePersist
    public void onPrePersist(){
        ZonedDateTime now = ZonedDateTime.now();
        setCreatedDate(now);
        setLastModifiedDate(now);
    }

    @PreUpdate
    public void onPreUpdate(){
        setLastModifiedDate(ZonedDateTime.now());
    }
}
