package com.blogapp.blog_app_apis.payloads;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {


    private Integer categoryId;
    @NotEmpty
    private String categoryTitle;
    @NotEmpty
    private String categoryDesc;
}
