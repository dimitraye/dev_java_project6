package com.paymybuddy.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account accountSender;
    @ManyToOne(fetch = FetchType.EAGER)
    private Account accountReceiver;
    private Date date;
    private double amount;
    private String description;
}
