package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Entities.Region;
import ma.org.comfybackend.security.Services.CommandService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/command")
public class CommandController {
    CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping(path = "/showRegions")
    public List<Region> showRegions(){
        return commandService.listRegions();
    }
}
