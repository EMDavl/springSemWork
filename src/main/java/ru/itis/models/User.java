package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.enums.Role;

import javax.persistence.*;
import java.util.Set;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
