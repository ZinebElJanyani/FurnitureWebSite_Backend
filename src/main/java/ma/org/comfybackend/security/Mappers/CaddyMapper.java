package ma.org.comfybackend.security.Mappers;

import ma.org.comfybackend.security.DTO.CaddyDTO;

import ma.org.comfybackend.security.Entities.Caddy;

import org.springframework.beans.BeanUtils;

public class CaddyMapper {

    public CaddyDTO fromCaddy(Caddy caddy){
        CaddyDTO caddyDTO = new CaddyDTO();

        BeanUtils.copyProperties(caddy,caddyDTO);
        return caddyDTO;
    }
}
