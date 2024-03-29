package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CommandDTO;
import ma.org.comfybackend.security.DTO.CommandShowDTO;
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
    public CommandDTO fromCommand(Command command){
        CommandDTO commandDTO = new CommandDTO();
        BeanUtils.copyProperties(command,commandDTO);
        return commandDTO;
    }

    public CommandShowDTO fromCommand2(Command command){
        CommandShowDTO commandShowDTO = new CommandShowDTO();
        BeanUtils.copyProperties(command,commandShowDTO);
        return commandShowDTO;
    }
}
