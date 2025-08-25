package io.github.nitishc.grievance.grievance_service.model;

import io.github.nitishc.grievance.grievance_service.util.Block;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;

    private int houseNo;

    private String street;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false ,columnDefinition = "ENUM('BHAUTIPRATAP', 'VIJAY_NAGAR', 'PHOOLBAGH', 'CANTT')")
    private Block block;

    @Setter(AccessLevel.NONE)
    private final String city = "Kanpur City";

    @Setter(AccessLevel.NONE)
    private final String state = "Uttar Pradesh";

    @Column(nullable = false)
    private String pincode;

    @OneToOne
    private Grievance grievance;

}
