package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.ProductDTO;

import java.util.Map;

public interface ChartService {
    Map<String, Object> statistics();

    Map<String,Object>  getMostOrdered();
}
