package com.slsb.expense.tracker.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequestDto {

    private Long categoryId;

    @NotBlank(message = "Category required.")
    @NotNull(message = "Invalid Category Name: Category Name is NULL")
    private String categoryName;
}
