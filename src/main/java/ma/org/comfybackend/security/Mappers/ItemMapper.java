package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.ItemDTO;
import ma.org.comfybackend.security.DTO.ProductDTO;
import ma.org.comfybackend.security.Entities.Item;
import ma.org.comfybackend.security.Entities.Product;
import org.springframework.beans.BeanUtils;

public class ItemMapper {
    public ItemDTO fromItem(Item item){
        ItemDTO itemDTO = new ItemDTO();

        BeanUtils.copyProperties(itemDTO,item);
        return itemDTO;
    }
}
