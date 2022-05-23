package ru.itis.models;

import lombok.*;
import ru.itis.models.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"subscribers", "subscriptions"})
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

    @OneToMany(mappedBy = "author", targetEntity = Post.class)
    private Set<Post> posts;

    @Column(name = "is_public_account")
    private boolean isPublicAccount;

    @ManyToMany()
    @JoinTable(
            name = "likes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
    )
    private Set<Post> likedPosts;

    @ManyToMany()
    @JoinTable(
            name = "dislikes",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id")
    )
    private Set<Post> dislikedPosts;

    public enum ACCOUNT_STATUS {
        CONFIRMED, NOT_CONFIRMED, BANNED
    }
}
