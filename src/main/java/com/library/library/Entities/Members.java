package com.library.library.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Members {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String memberId;
    private String name;
    private String address;
    private long phoneNumber;
    private String email;

//    @OneToMany(mappedBy = "members")
//    private List<Transactions> transactions;
}
