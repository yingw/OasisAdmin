package cn.wilmar.lte;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Yin Guo Wei 2018/3/27.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        logger.info("UserController.UserController");
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ModelAndView userList() {
        logger.info("UserController.userList");
        return new ModelAndView("user/userList", "users", userRepository.findAll());
    }

}