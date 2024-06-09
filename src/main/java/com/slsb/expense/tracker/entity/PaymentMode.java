package com.slsb.expense.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_mode",
        uniqueConstraints = @UniqueConstraint(columnNames={"payment_mode_name", "cashbook_id"})
)
public class PaymentMode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_mode_id", nullable = false)
    private Long paymentModeId;

    @Column(name = "payment_mode_name", nullable = false)
    @NotBlank(message = "Payment Mode Name is mandatory")
    private String paymentModeName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashbook_id", nullable = false)
    private CashBook cashBook;
}
