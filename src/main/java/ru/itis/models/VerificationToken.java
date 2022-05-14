package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "verification_code")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerificationToken {

    private static final long EXPIRATION_S = 60L * 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private User user;

    private LocalDateTime expires;

    private UUID value;

    public static LocalDateTime calculateExpires() {
        LocalDateTime dt = LocalDateTime.now();
        return dt.plusSeconds(EXPIRATION_S);
    }

    public static boolean isExpired(VerificationToken token) {
        return LocalDateTime.now().isAfter(token.getExpires());
    }

}
