package com.slsb.expense.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.slsb.expense.tracker.jwtAuth.user.Role;
import com.slsb.expense.tracker.util.expenseTrackerEnum.EntryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expense",
        uniqueConstraints = @UniqueConstraint(columnNames={"expense_name", "cashbook_id"})
)
public class Expense extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id", nullable = false)
    private Long expenseId;

    @Column(name = "expense_name", nullable = false)
    @NotBlank(message = "Expense Name is mandatory")
    private String expenseName;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Entry Type is mandatory.")
    private EntryType entryType;

    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "expense_date_time", nullable = false)
    private Date expenseDateTime;

    @Column(name = "amount", nullable = false)
    @NotNull(message = "Amount is mandatory.")
    private BigDecimal amount;

    @Column(name = "remarks")
    private String remarks;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentMode_id", nullable = false)
    private PaymentMode paymentMode;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashbook_id", nullable = false)
    private CashBook cashBook;
}
