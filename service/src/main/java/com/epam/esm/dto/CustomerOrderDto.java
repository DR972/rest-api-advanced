package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The class {@code CustomerOrderDto} represents CustomerOrderDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDto extends RepresentationModel<CustomerOrderDto> {
    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.dto.CustomerOrderDto} class
     * fields when creating a new CustomerOrderDto object
     */
    public interface OnCreate {
    }

    /**
     * long id.
     */
    private long id;
    /**
     * long customer id.
     */
    private long customer;
    /**
     * LocalDateTime purchaseTime.
     */
    private LocalDateTime purchaseTime;
    /**
     * List<GiftCertificateDto> giftCertificates.
     */
    @NotEmpty(message = "ex.giftCertificatesNotNull")
    @Valid
    private List<GiftCertificateDto> giftCertificates;
    /**
     * BigDecimal amount.
     */
    private BigDecimal amount;
}
