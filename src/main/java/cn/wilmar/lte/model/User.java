package cn.wilmar.lte.model;

import cn.wilmar.lte.model.AbstractAuditingEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author yinguowei@gmail.com 2018/3/27.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends AbstractAuditingEntity {
    private static final Random RANDOM = new Random();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull @NotNull @NotEmpty @Column(unique = true)
    String username;

    @NotNull @NotEmpty @NonNull
    String fullname;

    @JsonIgnore
    String password;

    @NonNull @NotEmpty @NotNull
    String email;

    @Enumerated(EnumType.STRING)
    Gender gender = Gender.randomGender();

    private Boolean active = RANDOM.nextBoolean();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public boolean isNew() {
        return this.id == null;
    }
//
//    @CreatedDate
//    LocalDateTime createdDate;
//    @CreatedBy
//    String createdBy;
//    @LastModifiedDate
//    LocalDateTime lastModifiedDate;
//    @LastModifiedBy
//    String lastModifiedBy;
}