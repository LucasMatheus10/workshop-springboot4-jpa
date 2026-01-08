package com.estudosjava.course.dto;

import java.time.Instant;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

public record PaymentDTO(
    @NotNull(message = "O instante do pagamento é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    Instant moment
) {}
