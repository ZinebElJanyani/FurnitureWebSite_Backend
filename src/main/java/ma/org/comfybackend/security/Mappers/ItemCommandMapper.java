package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CommandShowDTO;
import ma.org.comfybackend.security.DTO.ItemCommandDTO;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.CommandItem;
import org.springframework.beans.BeanUtils;

public class ItemCommandMapper {
    public ItemCommandDTO fromCommandItem(CommandItem item){
        ItemCommandDTO itemCommandDTO =new ItemCommandDTO();
        BeanUtils.copyProperties(item,itemCommandDTO);
        return itemCommandDTO;
    }
}
