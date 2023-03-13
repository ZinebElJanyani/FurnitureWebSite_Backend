package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Services.CaddyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/caddy")
public class CaddyController {
    private CaddyService caddyService;

    public CaddyController(CaddyService caddyService) {
        this.caddyService = caddyService;
    }

    @PostMapping(path = "/create/{customerEmail}")
    public Caddy CreateCaddy(@PathVariable String customerEmail){
        return caddyService.createCaddy(customerEmail);
    }
}
