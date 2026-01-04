package com.estudosjava.course.dto;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;
import com.estudosjava.course.entities.Payment;

public record PaymentDTO(
    @NotNull
    Long id,
    @NotNull
    Instant moment
) {
    public PaymentDTO(Payment payment) {
        this(payment.getId(), payment.getMoment());
    }
}
