package cn.wilmar.admin.web;

import cn.wilmar.admin.repository.RoleRepository;
import cn.wilmar.admin.service.ResourceService;
import cn.wilmar.admin.repository.ResourceRepository;
import org.springframework.stereotype.Controller;

/**
 * @author Yin Guo Wei 2018/4/8.
 */
@Controller
public class ResourceController {

    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;
    private final RoleRepository roleRepository;

    public ResourceController(ResourceService resourceService, ResourceRepository resourceRepository, RoleRepository roleRepository) {
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
        this.roleRepository = roleRepository;
    }

}
