package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The class {@code GiftCertificateDto} represents GiftCertificateDto.
 *
 * @author Dzmitry Rozmysl
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.dto.GiftCertificateDto} class
     * fields when creating a new GiftCertificateDto object
     */
    public interface OnCreate {
    }

    /**
     * The marker  interface {@code OnCreate} helps in providing validation of {@link com.epam.esm.dto.GiftCertificateDto} class
     * fields when updating GiftCertificateDto object
     */
    public interface OnUpdate {
    }

    /**
     * GiftCertificateDto id.
     */
    @Positive(groups = CustomerOrderDto.OnCreate.class, message = "ex.giftCertificateIdPositive")
    private long id;
    /**
     * GiftCertificateDto name.
     */
    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.certificateNameNotNull")
    @Size(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, min = 2, max = 30, message = "ex.certificateNameSize")
    private String name;
    /**
     * GiftCertificateDto description.
     */
    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.descriptionNotNull")
    @Size(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, min = 2, max = 200, message = "ex.descriptionSize")
    private String description;
    /**
     * GiftCertificateDto price.
     */
    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.priceNotNull")
    @Positive(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, message = "ex.pricePositive")
    private BigDecimal price;
    /**
     * GiftCertificateDto duration.
     */
    @NotNull(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.durationNotNull")
    @Positive(groups = {GiftCertificateDto.OnCreate.class, GiftCertificateDto.OnUpdate.class}, message = "ex.durationPositive")
    private Integer duration;
    /**
     * GiftCertificateDto createDate.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;
    /**
     * GiftCertificateDto lastUpdateDate.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;
    /**
     * List<Tag> tags.
     */
    @NotEmpty(groups = {GiftCertificateDto.OnCreate.class}, message = "ex.tagsNotNull")
    @Valid
    private List<TagDto> tags;

    /**
     * The constructor creates a GiftCertificateDto object
     *
     * @param name        String name
     * @param description String description
     * @param price       BigDecimal price
     * @param duration    int duration
     * @param tags        List<Tag> tags
     */
    public GiftCertificateDto(String name, String description, BigDecimal price, Integer duration, List<TagDto> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    /**
     * The constructor creates a GiftCertificateDto object
     *
     * @param name           String name
     * @param description    String description
     * @param price          BigDecimal price
     * @param duration       int duration
     * @param createDate     LocalDateTime createDate
     * @param lastUpdateDate LocalDateTime lastUpdateDate
     * @param tags           List<Tag> tags
     */
    public GiftCertificateDto(String name, String description, BigDecimal price, Integer duration, LocalDateTime createDate, LocalDateTime lastUpdateDate, List<TagDto> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }
}
