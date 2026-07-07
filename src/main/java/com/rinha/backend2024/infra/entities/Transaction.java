package com.rinha.backend2024.infra.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.OffsetDateTime;

@Entity
@Table(name = "tb_transaction")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaction")
    public BigInteger transactionId;

    @ManyToOne
    @JoinColumn(
            name = "id_client",
            foreignKey = @ForeignKey(name = "id_client")
    )
    private Client client;

    @Column(name = "valor")
    public BigDecimal value;

    @Column(name = "tipo")
    public char transactionType;

    @Column(name = "descricao")
    public String transactionDescription;

    @Column(name = "realizada_em")
    public OffsetDateTime transactionDate;

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", value=" + value +
                ", transactionType=" + transactionType +
                ", transactionDescription=" + transactionDescription +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
