package com.slsb.expense.tracker.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "active_flag")
    private int activeFlag;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "created_by_ip", nullable = false)
    private String createdByIp;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by_ip")
    private String updatedByIp;
}
