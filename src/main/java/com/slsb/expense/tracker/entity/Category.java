package com.slsb.expense.tracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.slsb.expense.tracker.jwtAuth.user.User;
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
@Table(name = "category",
        uniqueConstraints = @UniqueConstraint(columnNames={"category_name", "cashbook_id"})
)
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "category_name", nullable = false)
    @NotBlank(message = "Category Name is mandatory")
    private String categoryName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cashbook_id", nullable = false)
    private CashBook cashBook;
}
