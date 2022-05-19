package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
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
    @Null(groups = CustomerOrderDto.OnCreate.class, message = "ex.customerOrderIdNull")
    private long id;
    /**
     * long customer id.
     */
//    @NotNull(groups = CustomerOrderDto.OnCreate.class, message = "ex.CustomerIdNotNull")
//    @Positive(groups = CustomerOrderDto.OnCreate.class, message = "ex.customerIdPositive")
    private long customer;
    /**
     * LocalDateTime purchaseTime.
     */
    @Null(groups = CustomerOrderDto.OnCreate.class, message = "ex.customerOrderPurchaseTimeNull")
    private LocalDateTime purchaseTime;
    /**
     * List<GiftCertificateDto> giftCertificates.
     */
    @NotEmpty(groups = CustomerOrderDto.OnCreate.class, message = "ex.giftCertificatesNotNull")
    @Valid
    private List<GiftCertificateDto> giftCertificates;
    /**
     * BigDecimal amount.
     */
    @Null(groups = CustomerOrderDto.OnCreate.class, message = "ex.customerOrderAmountNull")
    private BigDecimal amount;
}
