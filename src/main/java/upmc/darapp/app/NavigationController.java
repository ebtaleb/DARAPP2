package upmc.darapp.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import upmc.darapp.users.model.User;
import upmc.darapp.users.model.UserRole;
import upmc.darapp.users.dao.UserDAO;

import upmc.darapp.api.model.Event;
import upmc.darapp.api.dao.EventDAO;

import upmc.darapp.api.dao.FollowDAO;

@Controller
public class NavigationController {

    @Autowired
    private EventDAO eventDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FollowDAO followDAO;

    @Autowired
    private AuthenticationManager authenticationManager;

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

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView profile() {
        return new ModelAndView("profile");
    }

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "ERREUR");
		}

		if (logout != null) {
			model.addObject("msg", "Déconnexion réussie");
		}
		model.setViewName("login");

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

		model.addAttribute("title", e.getTitle());
		model.addAttribute("desc", e.getDescr());
		model.addAttribute("addr", e.getAddress());
		model.addAttribute("date", e.getStart_date().toString());
		model.addAttribute("time", e.getStart_time().toString());
		model.addAttribute("path", e.getPath());
		model.addAttribute("lat", e.getLat());
		model.addAttribute("lng", e.getLng());
        model.addAttribute("event_id", id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        String html_button = "<button id='" + id + "' class='btn "; 

        if (followDAO.testUserForEvent(name, id)) {
            html_button += "btn-info unfollowUser'>Inscrit";
        } else {
            html_button += "btn-default followUser'>S'inscrire";
        }

        html_button += "</button>";
        model.addAttribute("button", html_button);

        return "single_event";
	}
}
