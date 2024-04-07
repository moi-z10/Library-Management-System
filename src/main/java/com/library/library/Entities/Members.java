package com.library.library.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Members {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long memberId;
    private String name;
    private String address;
    private long phoneNumber;
    private String email;

//    @OneToMany(mappedBy = "members")
//    private List<Transactions> transactions;
}
