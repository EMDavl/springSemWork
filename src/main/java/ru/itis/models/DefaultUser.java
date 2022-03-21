package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.itis.models.enums.Role;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultUser extends UsersBase{

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", targetEntity = ModeratedPost.class)
    private Set<ModeratedPost> moderatedPosts;

    @OneToMany(mappedBy = "author", targetEntity = UnmoderatedPost.class)
    private Set<ModeratedPost> unmoderatedPosts;

    @Column(name = "is_public_account")
    private boolean isPublicAccount;

}
