package cn.wilmar.admin.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Yin Guo Wei 2018/4/3.
 */
@Controller
public class ExampleController {

    final ExampleRepository exampleRepository;

    final ExampleService exampleService;

    public ExampleController(ExampleRepository exampleRepository, ExampleService exampleService) {
        this.exampleRepository = exampleRepository;
        this.exampleService = exampleService;
    }

    @GetMapping("/example")
    public ModelAndView findAllExamples() {
        return new ModelAndView("example/exampleList", "examples", exampleService.findAllExamples());
    }

    @GetMapping("/ztree")
    public String ztree() {
        return "example/ztree";
    }
}
