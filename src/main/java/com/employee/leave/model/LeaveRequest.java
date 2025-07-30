package com.employee.leave.model;

import java.time.LocalDate;

import com.employee.leave.enummessage.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Leave_Requests")
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leave_id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeid;

    @NotNull(message = "Start date is required")
    @Column(name = "startdate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startdate;

    @NotNull(message = "End date is required")
    @Column(name = "enddate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enddate;

    @NotBlank(message = "Leave type is required")
    @Column(name = "leave_type")
    private String leaveType;

    @NotBlank(message = "Reason is required")
    @Column(name = "reason")
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @NotNull(message = "Applied date is required")
    @Column(name = "applied_date")
    private LocalDate date;

    @Column(name = "total_leaves")
    private int total_leaves;
}
