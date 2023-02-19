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
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Objects.equals(title, receipt.title) && Objects.equals(shopTitle, receipt.shopTitle) &&
                Objects.equals(shopAddress, receipt.shopAddress) && Objects.equals(phoneNumber, receipt.phoneNumber) &&
                Objects.equals(cashierNumber, receipt.cashierNumber) && Objects.equals(creationDate, receipt.creationDate) &&
                Objects.equals(receiptProducts, receipt.receiptProducts) && Objects.equals(discountCard, receipt.discountCard) &&
                Objects.equals(discountCardPrice, receipt.discountCardPrice) && Objects.equals(promotionalPercent, receipt.promotionalPercent) &&
                Objects.equals(promotionalPrice, receipt.promotionalPrice) && Objects.equals(total, receipt.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, shopTitle, shopAddress, phoneNumber, cashierNumber, creationDate,
                receiptProducts, discountCard, discountCardPrice, promotionalPercent, promotionalPrice, total);
    }
}
