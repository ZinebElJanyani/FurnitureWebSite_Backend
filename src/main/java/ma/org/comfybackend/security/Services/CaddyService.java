package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CaddyDTO;
import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.Entities.Caddy;
import ma.org.comfybackend.security.Entities.Command;

import java.util.List;

public interface CaddyService {
    Caddy createCaddy(String customerEmail);

    int createItem(int customerId, int productId, int quantity);

    List<ItemDTO> showItems(int customerId);

    int deleteItem(int productId);

    int updateCaddy(CaddyDTO caddyDTO, int idCustomer);

    Caddy showCady(int customerId);

    List<ItemDTO> displayItems(int cid);


}
