package com.paymybuddy.transaction.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


/**
 * Model of the table Transfer
 */
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
    private Double amount;
    private String description;

    private double commission;
}
