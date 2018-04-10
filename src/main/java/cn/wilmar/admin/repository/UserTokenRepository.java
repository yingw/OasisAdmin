package cn.wilmar.admin.repository;

import cn.wilmar.admin.model.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author yinguowei 2017/8/8.
 */
public interface UserTokenRepository extends JpaRepository<UserToken, String> {
    List<UserToken> findByUsernameLike(String username);
}
