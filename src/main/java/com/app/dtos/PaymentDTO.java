package com.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDTO {
    private Integer id;
    private Integer customerId;
    private Integer bookingId;
    private String method;
    private Double amount;
    private String status;
    private LocalDateTime timestamp;
    private String transactionId;

}

