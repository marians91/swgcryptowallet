package com.swag.crypto.wallet.portfolio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crypto_info")
public class CurrencyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "asset_name")
    private String assetName;
    @Column(name = "code")
    private String code;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "market_cap")
    private double marketCap;
}
