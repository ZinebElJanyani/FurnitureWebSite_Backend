package ma.org.comfybackend.security.Controllers;

import com.mysql.cj.xdevapi.JsonArray;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Services.RecommandationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class RecommandationController {

    RecommandationService recommandationService;
    RecommandationController(RecommandationService recommandationService){
        this.recommandationService = recommandationService;
    }
    @GetMapping(path = "/getRecommandation/{id_customer}")
    public List<ProductDTO> showProducts(@PathVariable int id_customer){
        return recommandationService.listRecommandedProducts(id_customer);
    }
}
