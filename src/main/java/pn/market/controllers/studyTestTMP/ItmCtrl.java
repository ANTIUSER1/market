package pn.market.controllers.studyTestTMP;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class ItmCtrl {

    @GetMapping("/o")
    public String index(){
        return "redirect:/opa.html";
    }
}
