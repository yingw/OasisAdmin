package cn.wilmar.lte;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Yin Guo Wei 2018/3/27.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);
}