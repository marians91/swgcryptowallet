package com.swag.crypto.wallet.portfolio.entity;

import com.swag.crypto.wallet.user.entity.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="wallet")
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @Column(nullable=true)
    private Double currencyAmount;
    @Column(nullable=false)
    private Double btcAmount;
    @Column(nullable=false)
    private String assetName;
    @Column(nullable=false)
    private String code;
    @Column(nullable=true, unique=true)
    private String address;
    @Column(nullable=true, unique=true)
    private String privateKey;

}
