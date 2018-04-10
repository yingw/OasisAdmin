package cn.wilmar.admin.example;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Yin Guo Wei 2018/4/3.
 */
public interface ExampleRepository extends JpaRepository<Example, Long> {

    Optional<Example> findOneByUsername(String username);
}
