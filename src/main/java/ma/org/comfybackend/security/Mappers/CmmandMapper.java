package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CommandDTO;
import ma.org.comfybackend.security.DTO.CustomerRegisterDTO;
import ma.org.comfybackend.security.Entities.Command;
import ma.org.comfybackend.security.Entities.Customer;
import org.springframework.beans.BeanUtils;

public class CmmandMapper {
    public Command fromCommandDTO(CommandDTO commandDTO){
        Command command = new Command();
        BeanUtils.copyProperties(commandDTO,command);
        return command;
    }
}
