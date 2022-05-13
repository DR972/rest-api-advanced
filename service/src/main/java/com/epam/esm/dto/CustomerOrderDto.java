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

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOrderDto extends RepresentationModel<CustomerOrderDto> {
    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.dto.TagDto} class
     * fields when creating and updating a new TagDto object
     */
    public interface OnCreate {
    }

    /**
     * TagDto id.
     */
    private long id;
    private long customer;

    private LocalDateTime purchaseTime;
    @NotEmpty(message = "ex.giftCertificatesNotNull")
    @Valid
    private List<GiftCertificateDto> giftCertificates;

    private BigDecimal amount;
}
