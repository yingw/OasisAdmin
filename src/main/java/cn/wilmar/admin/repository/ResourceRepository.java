package cn.wilmar.admin.repository;

import cn.wilmar.admin.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Yin Guo Wei 2018/4/8.
 */
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
    List<Resource> findAllByOrderByParentAscIdAscSortAsc();
    Optional<Resource> findOneByResourceKey(String resourceKey);
}
