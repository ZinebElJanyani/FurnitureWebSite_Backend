package ma.org.comfybackend.security.Services;

import java.util.List;
import java.util.Map;

public interface ChartService {
    Map<String, Object> statistics();

    Map<String,Object>  getMostOrdered();

    Map<String, Long> getSalesPerCategory();

    List<Long> getCountRating();

    List<Long> getSalesPerMonths();
}
