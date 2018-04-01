package cn.yinguowei.oasis.web;

import cn.yinguowei.oasis.repository.UserTokenRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yinguowei 2017/8/8.
 */
@Controller
@Api(value = "用户session Web控制器")
public class UserTokenController {

    UserTokenRepository userTokenRepository;

    public UserTokenController(UserTokenRepository userTokenRepository) {
        this.userTokenRepository = userTokenRepository;
    }

    @ApiOperation(value = "查询tokens", notes = "根据用户名查询。\nToken是用户选择\"记住我\"后记录在数据库中的session对象。")
    @GetMapping("/tokens")
    public ModelAndView findUserTokens(
            @ApiParam(name = "用户姓名", required = false)
            @RequestParam(name = "username", defaultValue = "") String username) {
        return new ModelAndView("tokens/tokenList", "tokens", userTokenRepository.findByUsernameLike("%" + username + "%"));
    }

    @ApiOperation(value = "删除token", notes = "删除一个用户的登入记住状态。")
    @DeleteMapping("/tokens/{series}")
    public String deleteToken(
            @ApiParam(name = "用户series", required = true)
            @PathVariable("series") String series) {
        userTokenRepository.deleteById(series);
        return "redirect:/tokens";
    }

    @ApiOperation(value = "删除所有token", notes = "删除所有用户的登入记住状态。")
    @DeleteMapping("/tokens")
    public String deleteAllTokens() {
        userTokenRepository.deleteAll();
        return "redirect:/tokens";
    }

}
