package com.krushkov.virtualwallet.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "currencies")

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Currency {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "currency_code", length = 3)
    private String code;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "symbol", nullable = false, length = 5)
    private String symbol;

    @Column(name = "decimals", nullable = false)
    private Integer decimals;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
