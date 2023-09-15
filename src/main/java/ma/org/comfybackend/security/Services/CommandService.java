package ma.org.comfybackend.security.Services;

import ma.org.comfybackend.security.DTO.*;
import ma.org.comfybackend.security.Entities.City;
import ma.org.comfybackend.security.Entities.DeliveryAdress;
import ma.org.comfybackend.security.Entities.Region;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

public interface CommandService {
    List<Region> listRegions();

    List<City> listCities(int idR);

    int addAddress(int idCity, Map<String, Object> values);

    int addCommand(int idAddress, CommandDTO commandDTO);

    int addCreditPayment(int idCommand, CardDTO commandDTO);
    List<CommandShowDTO> displayCommand(int id);

    List<ItemCommandDTO> displayCommandItems(int idCommande);


    ByteArrayInputStream exportPDF(int idCommande);

    List<CommandDTO> displayAllCommands();

    int changeState(int idCommand, String stateValue);

    CommandShowDTO displayOnCommande(int id);

    List<DeliveryAdress> getSavedAdresses(int idCustomer);
}
