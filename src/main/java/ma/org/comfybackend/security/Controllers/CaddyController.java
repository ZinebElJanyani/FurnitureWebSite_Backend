package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.CaddyDTO;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Item;
import ma.org.comfybackend.security.Services.CaddyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path = "/addItem")
    public int CreateItem(@RequestParam("costomerId") int customer_id, @RequestParam("productId") int product_id, @RequestParam("quantity") int quantity){
        return caddyService.createItem(customer_id,product_id,quantity);
    }

    @GetMapping(path = "/showCart")
    public List<ItemDTO> showItems(@RequestParam("costomerId") int customer_id){
        return caddyService.showItems(customer_id);
    }
    @GetMapping(path = "/showCartInfo")
    public Caddy showCart(@RequestParam("costomerId") int customer_id){
        return caddyService.showCady(customer_id);
    }
    @DeleteMapping (path = "/deleteItem/{id}")
    public int deleteItem(@PathVariable int id){
        return caddyService.deleteItem(id);
    }

    @PutMapping(path = "/updateCaddy/{idCustomer}")
    public int updateCaddy(@RequestBody CaddyDTO caddyDTO,@PathVariable int idCustomer){
        return caddyService.updateCaddy(caddyDTO,idCustomer);
    }
    @GetMapping(path = "/getItems")
    public List<ItemDTO> getItems(@RequestParam("costomerId") int customer_id){
        return caddyService.displayItems(customer_id);
    }


}
