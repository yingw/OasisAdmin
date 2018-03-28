package cn.wilmar.lte.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * @author yingu on 2017/7/23.
 */
@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Role {
    @Id @GeneratedValue
    Long id;

    @NotEmpty @NonNull @Column(unique = true)
    String key;

    @NotEmpty @NonNull @Column(unique = true)
    String name;

    @Column(length = 500)
    String description;
}
