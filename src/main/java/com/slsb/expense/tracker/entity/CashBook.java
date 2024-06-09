package com.slsb.expense.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.slsb.expense.tracker.jwtAuth.user.Role;
import com.slsb.expense.tracker.jwtAuth.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cashbook",
        uniqueConstraints = @UniqueConstraint(columnNames={"cashbook_name", "user_id"})
)
public class CashBook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cashbook_id", nullable = false)
    private Long cashbookId;

    @Column(name = "cashbook_name", nullable = false)
    @NotBlank(message = "Cash Book Name is mandatory")
    private String cashbookName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
