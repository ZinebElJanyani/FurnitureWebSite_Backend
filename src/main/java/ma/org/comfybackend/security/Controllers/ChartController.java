package ma.org.comfybackend.security.Controllers;

import ma.org.comfybackend.security.DTO.ProductCDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Services.ChartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/chart")
public class ChartController {

    private ChartService chartService;

    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping(path = "/generalInfo")
    public Map<String,Object> getStatistic(){
        return this.chartService.statistics();
    }

    @GetMapping(path = "/mostOrdered")
    public Map<String,Object>  mostOrdered(){
        return this.chartService.getMostOrdered();
    }
}
