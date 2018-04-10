package cn.wilmar.admin.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yinguowei@gmail.com 2018/3/27.
 */
@Controller
public class DynamicController {
    private static final Logger logger = LoggerFactory.getLogger(DynamicController.class);

    @RequestMapping(value = {"/index.html", "/index2.html", "starter.html", "/*/*.html", "/*/*/*.html"})
    public String route(HttpServletRequest request) {
        logger.debug("DynamicController.route: request.getRequestURI() = {}", request.getRequestURI());
        String path = request.getRequestURI();
        return path.substring(0, path.length() - 5); // remove ".html"
    }

    @RequestMapping("/")
    public String home() {
        logger.debug("DynamicController.home");
        return "index";
    }
}