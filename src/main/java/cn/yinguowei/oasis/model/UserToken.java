package cn.yinguowei.oasis.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yinguowei 2017/8/8.
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@Immutable
@Table(name = "persistent_logins")
//@Subselect("select * from persistent_logins")
public class UserToken {
    @Id
    protected String series;
    @NotNull
    protected String username;
    @NotNull
    protected String token;
    @NotNull
    protected LocalDateTime lastUsed;
}
