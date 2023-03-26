package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.CardDTO;
import ma.org.comfybackend.security.DTO.CommandDTO;
import ma.org.comfybackend.security.Entities.City;
import ma.org.comfybackend.security.Entities.Region;

import javax.smartcardio.Card;
import java.util.List;
import java.util.Map;

public interface CommandService {
    List<Region> listRegions();

    List<City> listCities(int idR);

    int addAddress(int idCity, Map<String, Object> values);

    int addCommand(int idAddress, CommandDTO commandDTO);

    int addCreditPayment(int idCommand, CardDTO commandDTO);
}
