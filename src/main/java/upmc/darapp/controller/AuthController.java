package upmc.darapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
        public ModelAndView welcomePage() {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security Hello World");
            model.addObject("message", "This is welcome page!");
            model.setViewName("hello");
            return model;

        }

    @RequestMapping(value = "/admin**", method = RequestMethod.GET)
        public ModelAndView adminPage() {

            ModelAndView model = new ModelAndView();
            model.addObject("title", "Spring Security Hello World");
            model.addObject("message", "This is protected page!");
            model.setViewName("admin");

            return model;

        }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
        public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) {

            try {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }

                SecurityContextHolder.getContext().setAuthentication(null);
                SecurityContextHolder.clearContext();

            } catch (Exception e) {
            }

            ModelAndView mav = new ModelAndView();
            mav.setViewName("redirect:/");
            return mav;

        }

}
