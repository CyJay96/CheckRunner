package com.clevertec.checkrunner.domain;

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
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "checks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Check implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String shopAddress;

    private String phoneNumber;

    private Long cashierNumber;

    @CreatedDate
    private Date creationDate;

    @OneToMany(mappedBy = "check", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CheckProduct> products;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private DiscountCard discountCard;

    private boolean isPromotional;

    private BigDecimal taxableTot;

    private BigDecimal vat;

    private BigDecimal total;

}
