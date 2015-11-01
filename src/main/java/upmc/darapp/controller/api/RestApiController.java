package upmc.darapp.controller.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import upmc.darapp.model.Message;

@RestController
public class RestApiController {

    @RequestMapping("/hello/{player}")
    public Message message(@PathVariable String player) {

        Message msg = new Message(player, "Hello " + player);
        return msg;
    }
}
