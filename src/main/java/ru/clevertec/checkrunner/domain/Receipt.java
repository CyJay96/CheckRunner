package ru.clevertec.checkrunner.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "receipts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receipt implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String shopTitle;

    private String shopAddress;

    private String phoneNumber;

    private Long cashierNumber;

    @CreatedDate
    private OffsetDateTime creationDate;

    @OneToMany(mappedBy = "receipt", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.MERGE})
    private List<ReceiptProduct> receiptProducts;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private DiscountCard discountCard;

    private BigDecimal discountCardPrice;

    private BigDecimal promotionalPercent;

    private BigDecimal promotionalPrice;

    private BigDecimal total;
}
