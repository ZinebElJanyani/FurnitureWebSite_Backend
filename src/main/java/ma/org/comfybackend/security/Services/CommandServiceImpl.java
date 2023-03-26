package ma.org.comfybackend.security.Services;
import ma.org.comfybackend.security.DTO.CardDTO;
import ma.org.comfybackend.security.Repositories.*;
import org.apache.commons.lang3.RandomStringUtils;
import ma.org.comfybackend.security.DTO.CommandDTO;
import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Mappers.CmmandMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommandServiceImpl implements CommandService{

    RegionRepository regionRepository;
    CityRepository cityRepository;
    CustomerRepository customerRepository;
    EmailService emailService;
    private final DeliveryAdressRepository deliveryAdressRepository;
    CmmandMapper commandMapper;
    private final CommandRepository commandRepository;
    private final CardPaymentRepository cardPaymentRepository;

    public CommandServiceImpl(RegionRepository regionRepository,CityRepository cityRepository,CustomerRepository customerRepository,
                              DeliveryAdressRepository deliveryAdressRepository,
                              CommandRepository commandRepository,
                              CardPaymentRepository cardPaymentRepository,EmailService emailService) {
        this.regionRepository = regionRepository;
        this.cityRepository = cityRepository;
        this.customerRepository=customerRepository;
        this.deliveryAdressRepository = deliveryAdressRepository;
        this.commandMapper = new CmmandMapper();
        this.commandRepository = commandRepository;
        this.cardPaymentRepository = cardPaymentRepository;
        this.emailService = emailService;
    }

    @Override
    public List<Region> listRegions() {
        return regionRepository.findAll();
    }

    @Override
    public List<City> listCities(int idR) {
        Region r = regionRepository.findById(idR).orElse(null);
        return cityRepository.findByRegion(r);
    }

    @Override
    public int addAddress(int idCity, Map<String, Object> values) {
        City city =cityRepository.findById(idCity).orElse(null);
        DeliveryAdress deliveryAdress = new DeliveryAdress();
        deliveryAdress.setCity(city);
        deliveryAdress.setAddess(values.get("address").toString());
        Customer c = customerRepository.getById(Integer.parseInt(values.get("idC").toString()));
        deliveryAdress.setCustomer(c);

        deliveryAdress.setSaved(Boolean.parseBoolean(values.get("isSaved").toString()));
        DeliveryAdress d = deliveryAdressRepository.save(deliveryAdress);
        return d.getId();
    }

    @Override
    public int addCommand(int idAddress, CommandDTO commandDTO) {
        Command command = this.commandMapper.fromCommandDTO(commandDTO);

        DeliveryAdress d = deliveryAdressRepository.findById(idAddress).orElse(null);
        command.setDeliveryAdress(d);

        String code = RandomStringUtils.randomAlphanumeric(10);
        command.setRef(code);

        Customer customer = d.getCustomer();
        customer.setName(commandDTO.getName());
        customer.setPhone(commandDTO.getPhone());
        customer.setEmail(commandDTO.getEmail());
        customerRepository.save(customer);
        command.setCustomer(customer);
        Command c = commandRepository.save(command);

        //sending email:
        this.emailService.sendMessage(//
                customer.getEmail(),//
                "Hello there",//
                "I hope this work"//
        );

        return c.getId();
    }

    @Override
    public int addCreditPayment(int idCommand, CardDTO cardDTO) {
        CardPayment cardPayment = new CardPayment();
        cardPayment.setName(cardDTO.getName());
        cardPayment.setCVC(cardDTO.getCVC());
        cardPayment.setCardNumber(cardDTO.getCardNumber());



       CardPayment crd =  cardPaymentRepository.save(cardPayment);
        Command c = commandRepository.findById(idCommand).orElse(null);
        c.setCardPayment(crd);
        commandRepository.save(c);
        return crd.getId();
    }
}
