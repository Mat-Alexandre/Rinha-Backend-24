package com.rinha.backend2024.infra.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_clients")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @Column(name = "id")
    public Integer clientId;

    @Column(name = "saldo")
    public Integer balance;

    @Column(name = "limite")
    public Integer limit;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", balance=" + balance +
                ", limit=" + limit +
                '}';
    }
}
