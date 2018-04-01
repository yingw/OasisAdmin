package cn.yinguowei.oasis.repository;

import cn.yinguowei.oasis.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

/**
 * @author yingu on 2017/7/23.
 */
@RepositoryRestResource
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findOneByKey(String key);
    Optional<Role> findOneByName(String name);
}
