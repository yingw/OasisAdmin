package cn.yinguowei.oasis.example;

import cn.yinguowei.oasis.model.AbstractAuditingEntity;
import cn.yinguowei.oasis.model.Gender;
import cn.yinguowei.oasis.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yinguowei 2018/3/27.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Example extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull @NotNull @NotEmpty @Column(unique = true)
    String username;

    @Column(length = 500)
    String description;

    @JsonIgnore
    String password;

    @NonNull @NotEmpty @NotNull
    String email;

    float floatField;
    //parent
    int number;

    @Enumerated(EnumType.STRING)
    Gender gender = Gender.randomGender();

    private Boolean enable;

    //children
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }

}