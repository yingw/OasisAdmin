package cn.wilmar.evo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * 创建 by 殷国伟 于 2018/2/19.
 */
@Controller
public class RoleController {

//    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/role")
    public String getAllRoles() {
//        restTemplate
        return "role/roleList";
    }
}
