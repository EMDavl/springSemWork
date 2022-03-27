package ru.itis.models;

import lombok.*;
import ru.itis.models.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"moderatedPosts", "unmoderatedPosts", "likedPosts", "dislikedPosts"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", targetEntity = ModeratedPost.class)
    private Set<ModeratedPost> moderatedPosts;

    @OneToMany(mappedBy = "author", targetEntity = UnmoderatedPost.class)
    private Set<ModeratedPost> unmoderatedPosts;

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
}
