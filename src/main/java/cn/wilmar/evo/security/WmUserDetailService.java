package cn.wilmar.evo.security;

import cn.wilmar.evo.model.User;
import cn.wilmar.evo.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Yin Guo Wei 2018/3/29.
 */
@Component
public class WmUserDetailService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(WmUserDetailService.class);

    private final UserRepository userRepository;

    public WmUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("MyUserDetailService.loadUserByUsername, username = {}", username);
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("怎么传了个空的用户名？");
        }

        Optional<User> user = userRepository.findOneByUsername(username);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("没找到用户：" + username);
        }

        List<GrantedAuthority> grantedAuthorities = user.get().getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getKey()))
                .collect(Collectors.toList());
        //user.get().getFullname(), 这里原来是username，作为显示的名称
        return new org.springframework.security.core.userdetails.User(username,
                user.get().getPassword(),
                grantedAuthorities);
    }
}
