package umc.forgrad.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "Free")
@IdClass(FreePk.class)
public class Free {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    @Column(name = "STUID")
    private Long stuid;

    @Column(name = "MEMO")
    private String memo;

}
