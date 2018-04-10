package cn.wilmar.admin.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yinguowei 2017/8/7.
 */
@Entity
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserLoginLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    @NotNull
    @Column(updatable = false)
    private String lastLoginIp;
    @NonNull
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime logDate;
    @Column(updatable = false)
    private String userAction;
}
