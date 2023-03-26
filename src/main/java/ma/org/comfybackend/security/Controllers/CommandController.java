package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CardDTO;
import ma.org.comfybackend.security.DTO.CommandDTO;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.City;
import ma.org.comfybackend.security.Entities.Product;
import ma.org.comfybackend.security.Entities.Region;
import ma.org.comfybackend.security.Services.CommandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping(path = "/showCity/{idR}")
    public List<City> showCities(@PathVariable int idR){
        return commandService.listCities(idR);
    }



   @PostMapping(path = "/createAddress/{idCity}")
    public int createAddress(@RequestBody Map<String, Object> values, @PathVariable int idCity){
        return commandService.addAddress(idCity,values);
    }

    @PostMapping(path = "/create/{idAddress}")
    public int createCommand(@RequestBody CommandDTO commandDTO, @PathVariable int idAddress){
        return commandService.addCommand(idAddress,commandDTO);
    }

    @PostMapping(path = "/createCreditCard/{idCommand}")
    public int createCreditCard(@RequestBody CardDTO cardDTO, @PathVariable int idCommand){
        return commandService.addCreditPayment(idCommand,cardDTO);
    }
}
