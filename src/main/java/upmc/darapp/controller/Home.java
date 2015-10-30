package upmc.darapp.controller;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
public class Home {
    @RequestMapping("/home")
    public ModelAndView home()
    {
        return new ModelAndView("index");
    }
}

