package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {
    private Integer id;
    private Integer customerId;
    private Integer roomId;
    private String content;
    private Integer rating;
    private String createdAt;

}

