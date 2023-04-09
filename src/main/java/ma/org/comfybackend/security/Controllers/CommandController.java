package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.*;
import ma.org.comfybackend.security.Entities.City;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Region;
import ma.org.comfybackend.security.Services.CommandService;
import ma.org.comfybackend.security.Services.EmailService;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/command")
public class CommandController {
    CommandService commandService;
    EmailService emailService;

    public CommandController(CommandService commandService,EmailService emailService) {
        this.commandService = commandService;
        this.emailService =emailService;
    }

    @GetMapping(path = "/showRegions")
    public List<Region> showRegions(){
        return commandService.listRegions();
    }

    @GetMapping(path = "/showCity")
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
    @GetMapping(path = "/sendMessage")
    public String senEmail(){

        this.emailService.sendMessage(//
                "zinebeljanyani@gmail.com",//
                "Hello there",//
                "I hope this work"//
        );
        return "message sent";
    }
    @GetMapping(path = "/getCommand/{idC}")
    public List<CommandShowDTO> getCommand(@PathVariable int idC){
        return commandService.displayCommand(idC);
    }

    @GetMapping(path = "/getCommandItems/{idCommande}")
    public List<ItemCommandDTO> getCommandItems(@PathVariable int idCommande){
        return commandService.displayCommandItems(idCommande);
    }


    @GetMapping(path = "/getInvoice/{idCommande}")
    public ResponseEntity<InputStreamResource> getInvoice(@PathVariable int idCommande){
       ByteArrayInputStream bais= commandService.exportPDF(idCommande);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline;filename=Command_Invoice"+idCommande+".pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));
    }


}
