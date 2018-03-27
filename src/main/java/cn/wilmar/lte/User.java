package cn.wilmar.lte;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yinguowei@gmail.com 2018/3/27.
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NonNull @NotNull @Column(unique = true)
    String username;
    @NotNull @NonNull
    String fullname;

    @JsonIgnore
    String password;

    String email;

    private Boolean active;

    public boolean isNew() {
        return this.id == null;
    }

    @CreatedDate
    LocalDateTime createdDate;
    @CreatedBy
    String createdBy;
    @LastModifiedDate
    LocalDateTime lastModifiedDate;
    @LastModifiedBy
    String lastModifiedBy;
}