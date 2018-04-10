package cn.wilmar.admin.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author Yin Guo Wei 2018/4/8.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Resource extends AbstractAuditingEntity {
    public Resource setParent(Resource parent) {
        this.parent = parent;
        return this;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NonNull
    String name;

    @NonNull @Column(unique = true)
    String resourceKey;

    @NonNull
    Integer sort;

    @NonNull
    Integer level;

    @ManyToOne(fetch = FetchType.EAGER)
    Resource parent;
}
