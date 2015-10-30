package upmc.darapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class SandboxController {

    @RequestMapping("/what")
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("home");
    }

    @RequestMapping("/aController")
    public void sayHello(HttpServletResponse response) throws IOException {
        response.sendError(500, "Here's a controller in action");
    }
}
