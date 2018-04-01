package cn.yinguowei.oasis.security;

import cn.yinguowei.oasis.repository.UserRepository;
import cn.yinguowei.oasis.config.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handle Login successfully actions: save Full Name in session, save login log.
 *
 * @author yinguowei 2017/8/7.
 */
@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String USER_FULL_NAME = "userFullName";
    public static final String USER_ROLE_NAME = "userRoleName";
    private static final String UNKNOWN = "unknown";
    Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);
    @Autowired
    UserRepository userRepository;

    private static boolean isUserAdmin(User user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_" + DataLoader.DEFAULT_ADMIN)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        //获得授权后可得到用户信息   可使用SUserService进行数据库操作
        User userDetails = (User) authentication.getPrincipal();
        request.getSession().setAttribute(USER_FULL_NAME, userRepository.findByUsername(userDetails.getUsername()).getFullname());
        request.getSession().setAttribute(USER_ROLE_NAME, isUserAdmin(userDetails) ? DataLoader.DEFAULT_ADMIN_ROLE_NAME : DataLoader.DEFAULT_USER_ROLE_NAME);

        logger.debug("session.userFullName = " + request.getSession().getAttribute(USER_FULL_NAME));
       /* Set<SysRole> roles = userDetails.getSysRoles();*/
        //输出登录提示信息
        logger.debug("User " + userDetails.getUsername() + " login!");

        logger.debug("IP :" + getIpAddress(request));

        super.onAuthenticationSuccess(request, response, authentication);
        logger.debug("request = " + request.getSession().getAttribute(USER_FULL_NAME));
    }

    public String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}