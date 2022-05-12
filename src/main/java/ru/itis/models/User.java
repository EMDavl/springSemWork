package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private ACCOUNT_STATUS status;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", targetEntity = ModeratedPost.class)
    private Set<ModeratedPost> moderatedPosts;

    @OneToMany(mappedBy = "author", targetEntity = UnmoderatedPost.class)
    private Set<UnmoderatedPost> unmoderatedPosts;

    @Column(name = "is_public_account")
    private boolean isPublicAccount;

    @ManyToMany()
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(referencedColumnName = "id", name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id", name = "subscribee_id")
    )
    private Set<User> subscribers;

    @ManyToMany(mappedBy = "subscribers")
    private Set<User> subscriptions;

    @ManyToMany()
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
    )
    private Set<ModeratedPost> likedPosts;

    @ManyToMany()
    @JoinTable(
            name = "dislikes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
    )
    private Set<ModeratedPost> dislikedPosts;

    public enum ACCOUNT_STATUS {
        CONFIRMED, NOT_CONFIRMED, BANNED
    }
}
