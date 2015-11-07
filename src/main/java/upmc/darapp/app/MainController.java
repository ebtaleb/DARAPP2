package upmc.darapp.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.*;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.beans.factory.annotation.Autowired;

import upmc.darapp.api.dao.EventDAO;
import upmc.darapp.api.model.Event;
import upmc.darapp.users.dao.UserDAO;
import upmc.darapp.users.model.User;
import upmc.darapp.users.model.UserRole;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import javax.validation.Valid;
import org.springframework.ui.ModelMap;

@Controller
public class MainController {

    @Autowired
    EventDAO eventDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationManager authenticationManager;

	@RequestMapping(value = "/home**", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public ModelAndView main() {
		return new ModelAndView("main");
	}

	@RequestMapping(value = "/newevent", method = RequestMethod.GET)
	public ModelAndView newEvent() {
		return new ModelAndView("event_registration_form");
	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView admin() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security + Hibernate Example");
		model.addObject("message", "This page is for ROLE_ADMIN only!");
		model.setViewName("admin");

		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}

		if (logout != null) {
			model.addObject("msg", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}

	// customize the error message
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "Invalid username and password!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "Invalid username and password!";
		}

		return error;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);

			model.addObject("username", userDetail.getUsername());

		}

		model.setViewName("403");
		return model;
	}

    @ModelAttribute("user")
    public User getUser(){
        return new User();
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        userDAO.createNewUser(user);
        userDAO.createNewUserRole(user);
        authenticateUserAndSetSession(user, request);

        return "redirect:/app/main/";
    }

    private void authenticateUserAndSetSession(User user, HttpServletRequest request) {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }

    @RequestMapping(value = "/event/{id}", method = RequestMethod.GET)
	public String event(@PathVariable("id") int id, ModelMap model) {
        Event e = eventDAO.get(id);
		model.addAttribute("name", e.getName());
		model.addAttribute("desc", e.getDescr());
		model.addAttribute("addr", e.getAddress());
		model.addAttribute("lat", e.getLat());
		model.addAttribute("lng", e.getLng());

        return "single_event";
	}
}
