package cn.wilmar.admin.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author yinguowei 2017/7/23.
 */
@Entity
//@Getter
//@Setter
@Data
//@ToString(exclude = "resources")
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Role extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotEmpty @NonNull @Column(unique = true, name = "role_key")
    String key;

    @NotEmpty @NonNull @Column(unique = true)
    String name;

    @Enumerated(EnumType.STRING) @NonNull @NotNull
    RoleType type;

    @Column(length = 500)
    String description;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Resource> resources;

}

