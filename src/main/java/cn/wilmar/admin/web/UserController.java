package cn.wilmar.admin.web;

import cn.wilmar.admin.model.Role;
import cn.wilmar.admin.model.User;
import cn.wilmar.admin.repository.RoleRepository;
import cn.wilmar.admin.repository.UserRepository;
import cn.wilmar.admin.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String USER_FORM = "user/userForm";
    private static final String USER_LIST = "user/userList";
    private static final String USER_GRANT = "user/userGrant";
    private static final String USER_DETAILS = "user/userDetails";
    private static final String DEFAULT_PASSWORD = "111111";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;
    @Autowired BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, UserService userService) {
        this.userService = userService;
        logger.debug("UserController.UserController");
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // ?? what for?
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ApiOperation(value = "查询所有用户", notes = "返回所有User对象的JSON格式")
    @GetMapping("/users")
    public String queryUsers(
            @ApiParam(name = "name", value = "用户名称查询条件", required = false)
            @RequestParam(defaultValue = "") String name, Model model, Principal principal) {
        logger.debug(">>>> UserController.queryUsers");
        logger.debug("name = " + name);
        // TODO find by name
        List<User> users;
        if (name.equals("")) {
            users = userService.findAllUsers();
        } else {
            users = userRepository.findByUsernameLikeOrFullnameLike("%" + name + "%", "%" + name + "%");
        }
        model.addAttribute("users", users);
        logger.debug("UserController.queryUsers <<<<");
        return USER_LIST;
    }

    @ApiOperation(value = "新增用户初始化", notes = "创建空的User对象返回页面")
    @GetMapping(value = "/users/new")
    public ModelAndView initCreationForm() {
        ModelAndView mav = new ModelAndView(USER_FORM, "user", new User());
//        mav.addObject("allRoles", roleRepository.findAll());
        return mav;
    }

    @ApiOperation(value = "新增保存", notes = "新增用户，并保存入库")
    @PostMapping("/users/new")
    public String createUser(
            @ApiParam(name = "用户对象", value = "用户对象，必要字段：姓名、用户名", required = true)
            @Valid User user, BindingResult result) {
        logger.info("UserController.createUser()： user = [" + user + "], result = [" + result + "]");
        if (result.hasErrors()) {
            return USER_FORM;
        }
        user.setPassword(bCryptPasswordEncoder.encode(DEFAULT_PASSWORD));
//        userRepository.save(user);
        userService.createUser(user);
        return "redirect:/users/" + user.getId();
    }

    @ApiOperation(value = "详情页面")
    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable("id") long id) {
        System.out.println(">>>> UserController.getUser");
        logger.debug("getUser: " + id);
        final User user = userService.getUserById(id);
        System.out.println("UserController.getUser <<<<");
        return new ModelAndView(USER_DETAILS, "user", user);
    }

    @ApiOperation(value = "编辑，初始化")
    @GetMapping(value = "/users/{id}/edit")
    public ModelAndView initEditForm(@PathVariable("id") Long id) {
        User user = userRepository.findById(id).get();
        ModelAndView mav = new ModelAndView(USER_FORM, "user", user);
//        mav.addObject("allRoles", roleRepository.findAll());
        return mav;
    }

    @ApiOperation(value = "编辑，保存")
    @PutMapping("/users/{id}/edit")
    public String updateUser(@PathVariable("id") Long id, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> logger.error(e.toString()));
            return USER_FORM;
        }
        // TODO mapper
        User u = userRepository.findById(id).get();
        u.setUsername(user.getUsername());
        u.setFullname(user.getFullname());
        u.setEmail(user.getEmail());
//        u.setRoles(user.getRoles());
        u.setActive(user.getActive());
        u.setGender(user.getGender());
//        userRepository.save(u);
        userService.updateUser(u);
        /*
        userRepository.findOneByUsername(user.getUsername()).ifPresent(u -> {
            u.setStatus(user.getStatus());
            u.setFullname(user.getFullname());
            u.getRoles().clear();
            u.getRoles().addAll(
                    user.getRoles().stream().map(roleRepository::findOneByName)
                            .collect(Collectors.toSet()));
//            u.setRoles(user.getRoles());
            userRepository.save(u);
        });
*/
//                }
//                user.setId(id);
//        userRepository.save(user);
        return "redirect:/users/{id}";
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        logger.debug("UserController.deleteUser {}", id);
//        userRepository.deleteById(id);
        userService.deleteUser(id);
        return "redirect:/users";
    }


    @ApiOperation(value = "激活用户")
//    @PreAuthorize("hasRole('admin')")
    @PutMapping("/users/{id}/enable")
    @ResponseBody
    public void enableUser(@PathVariable("id") Long id) {
        setUserStatus(id, true);
    }

    @ApiOperation(value = "关闭用户（非删除）")
    @PutMapping("/users/{id}/disable")
    @ResponseBody
    public void disableUser(@PathVariable("id") Long id) {
        setUserStatus(id, false);
    }

    private void setUserStatus(@PathVariable("id") Long id, boolean active) {
        userService.setUserStatus(id, active);
    }

    @GetMapping("/users/{id}/grant")
    public ModelAndView initGrantForm(@PathVariable Long id) {
        User user = userService.getUserById(id);
        ModelAndView mav = new ModelAndView(USER_GRANT, "user", user);
        mav.addObject("allRoles", roleRepository.findAll());
        return mav;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    @PostMapping("/users/{id}/grant")
    public User grantUser(@PathVariable Long id, @RequestParam(required = false) String[] roleIds) {
        User user = userService.getUserById(id);

        if (roleIds == null || roleIds.length == 0) {
            user.getRoles().clear();
            userService.updateUser(user);
            return user;
        }
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            if (roleId.equals("")) {
                continue;
            }
            roles.add(roleRepository.findById(Integer.parseInt(roleId)).get());
        }
        user.setRoles(roles);
        userService.updateUser(user);
        return user;
    }

}