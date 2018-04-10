package cn.wilmar.admin.example;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Yin Guo Wei 2018/4/3.
 */
@Service
@CacheConfig(cacheNames = "examples")
public class ExampleService {

    final ExampleRepository exampleRepository;

    public ExampleService(ExampleRepository exampleRepository) {
        this.exampleRepository = exampleRepository;
    }

    @Cacheable
    public List<Example> findAllExamples() {
        return exampleRepository.findAll();
    }
}
