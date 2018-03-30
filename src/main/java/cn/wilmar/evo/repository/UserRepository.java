package cn.wilmar.evo.repository;

import cn.wilmar.evo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author yingu on 2017/7/18.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameLikeOrFullnameLike(String username, String fullname);

    User findByUsername(String username);

    Optional<User> findOneByUsername(String username);
}
