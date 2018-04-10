package cn.wilmar.admin.web;

import cn.wilmar.admin.model.Resource;
import cn.wilmar.admin.model.Role;
import cn.wilmar.admin.model.vo.ZtreeView;
import cn.wilmar.admin.repository.RoleRepository;
import cn.wilmar.admin.repository.ResourceRepository;
import cn.wilmar.admin.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yinguowei 2018/2/19.
 */
@Controller
public class RoleController {
    //    public static final String REST_SERVICE_URI = "http://localhost:8080/api";
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    private static final String ROLE_FORM = "role/roleForm";
    private static final String ROLE_LIST = "role/roleList";
    private static final String ROLE_DETAILS = "role/roleDetails";
//    final RestTemplate restTemplate;

//    public RoleController(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    private final RoleRepository roleRepository;
    private final ResourceService resourceService;
    private final ResourceRepository resourceRepository;

    public RoleController(RoleRepository roleRepository, ResourceService resourceService, ResourceRepository resourceRepository) {
        this.roleRepository = roleRepository;
        this.resourceService = resourceService;
        this.resourceRepository = resourceRepository;
    }

    @GetMapping("/roles")
    public ModelAndView getAllRoles() {
//        List<LinkedHashMap<String, Object>> roles = restTemplate.getForObject(REST_SERVICE_URI + "/roles", List.class);
//        logger.debug("roles = " + roles);
//        restTemplate
        return new ModelAndView("role/roleList", "roles", roleRepository.findAll());
    }

    /**
     * TODO： 保存权限
     *
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/roles/{id}/grant")
//    @PostMapping(name = "/roles/{id}/grant", produces = "application/json;charset=UTF-8")
//    @PostMapping(value = "/roles/{id}/grant", produces = "application/json;charset=UTF-8")
    public Role grantSave(@PathVariable Integer id,
                          @RequestParam(required = false) String[] resourceIds) {
        Role role = roleRepository.findById(id).get();

        Set<Resource> resources = new HashSet<>();
        for (String s : resourceIds) {
            if (s.equals("")) {
                continue;
            }
            resources.add(resourceRepository.findById(Integer.parseInt(s)).get());
        }
        role.setResources(resources);
        roleRepository.save(role);
        return role;
    }

    /**
     * 打开设置权限页面
     *
     * @param id
     * @return
     */
    @GetMapping("/roles/{id}/grant")
    public ModelAndView grant(@PathVariable Integer id) {
        return new ModelAndView("role/roleGrant", "role", roleRepository.findById(id).get());
    }

    /**
     * Ajax 获取权限树
     *
     * @param roleId
     * @return
     */
    @ResponseBody
    @PostMapping("/roles/{roleId}/tree")
    public List<ZtreeView> getResources(@PathVariable Integer roleId) {
        // 角色的当前权限
        Set<Resource> roleResources = roleRepository.findById(roleId).get().getResources();

        List<ZtreeView> resultTreeNodes = new ArrayList<>();

        List<Resource> allResources = resourceService.findAllResources();
        // 循环产生 ZtreeView 对象，有的权限默认打钩
        for (Resource res : allResources) {
            ZtreeView node = new ZtreeView();
            node.setId(res.getId());
            node.setPId(res.getParent() == null ? Integer.valueOf(-1) : res.getParent().getId());
            node.setName(res.getName());
            node.setChecked(roleResources != null && roleResources.contains(res));
            resultTreeNodes.add(node);
        }
        return resultTreeNodes;
    }
}
