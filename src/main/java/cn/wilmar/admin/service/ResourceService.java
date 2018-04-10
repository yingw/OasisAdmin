package cn.wilmar.admin.service;

import cn.wilmar.admin.model.Resource;
import cn.wilmar.admin.repository.ResourceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Yin Guo Wei 2018/4/8.
 */
@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @Transactional(readOnly = true)
    public List<Resource> findAllResources() {
        return resourceRepository.findAllByOrderByParentAscIdAscSortAsc();
    }
}
