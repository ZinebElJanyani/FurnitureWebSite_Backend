package ma.org.comfybackend.security.Services;
import ma.org.comfybackend.security.DTO.*;
import ma.org.comfybackend.security.Mappers.ProductMapper;
import ma.org.comfybackend.security.Repositories.*;
import org.apache.commons.lang3.RandomStringUtils;
import ma.org.comfybackend.security.Entities.*;
import ma.org.comfybackend.security.Mappers.CmmandMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final CaddyRepository caddyRepository;
    private final CommandItemRepository commanditemRepository;
    private final ProductRepository productRepository;

    ProductMapper productMapper;
    public CommandServiceImpl(RegionRepository regionRepository,CityRepository cityRepository,CustomerRepository customerRepository,
                              DeliveryAdressRepository deliveryAdressRepository,
                              CommandRepository commandRepository,
                              CardPaymentRepository cardPaymentRepository,EmailService emailService,
                              CaddyRepository caddyRepository,
                              CommandItemRepository commanditemR,
                              ProductRepository productRepository) {
        this.regionRepository = regionRepository;
        this.cityRepository = cityRepository;
        this.customerRepository=customerRepository;
        this.deliveryAdressRepository = deliveryAdressRepository;
        this.commandMapper = new CmmandMapper();
        this.commandRepository = commandRepository;
        this.cardPaymentRepository = cardPaymentRepository;
        this.emailService = emailService;
        this.caddyRepository = caddyRepository;
        this.commanditemRepository = commanditemR;
        this.productRepository = productRepository;
        this.productMapper = new ProductMapper();
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

        command.setCustomer(customer);
        Command c = commandRepository.save(command);

        if(c!=null){
            Caddy caddy = caddyRepository.findByCustomer(customer);

            List<Item> cardItems = (List<Item>)caddy.getItems();
            for(Item item:cardItems){
                CommandItem commandItem = new CommandItem();
                commandItem.setQuantity(item.getQuantity());
                commandItem.setPrice(item.getProduct().getPrice());
                commandItem.setProduct(item.getProduct());
                commandItem.setCommand(c);
                this.commanditemRepository.save(commandItem);

                //changing  the quantity of products:
                Product p = item.getProduct();
                int Q=p.getQteStock() - item.getQuantity();

                p.setQteStock(Q);
                this.productRepository.save(p);
            }
        }

        //sending email:

        /*
        String message = "Dear " + customer.getName() + ",\n\nThank you for choosing Comfy for your furniture needs. We appreciate your recent order and we are honored to have the opportunity to provide you with stylish and comfortable furniture for your home.\n\n" +
                "We want you to know that your order has been received and is being prepared for shipment. Our team is dedicated to ensuring that your order is carefully packaged and delivered to you as quickly as possible. Your order is expected to be delivered to you by " + c.getDeliveryDate().toString() + ". We will also keep you informed about the status of your order and provide you with a tracking number so you can monitor its progress.\n\n" +
                "If you have any questions or concerns about your order, please do not hesitate to contact our customer service team. We are always here to help and ensure that your experience with Comfy is nothing but positive.\n\n" +
                "Thank you again for choosing Comfy.\n\n We appreciate your business and look forward to serving you in the future.\n\nBest regards,\nComfy";

        this.emailService.sendMessage(customer.getEmail(), "Thank You for Your Order from Comfy!", message);*/

      /*  String message = "Dear " + customer.getName() + ",\n\nThank you for choosing Comfy for your furniture needs. We appreciate your recent order and we are honored to have the opportunity to provide you with stylish and comfortable furniture for your home.\n\n" +
                "We want you to know that your order has been received and is being prepared for shipment. Our team is dedicated to ensuring that your order is carefully packaged and delivered to you as quickly as possible. Your order is expected to be delivered to you by " + c.getDeliveryDate().toString() + ". We will also keep you informed about the status of your order and provide you with a tracking number so you can monitor its progress.\n\n" +
                "If you have any questions or concerns about your order, please do not hesitate to contact our customer service team. We are always here to help and ensure that your experience with Comfy is nothing but positive.\n\n" +
                "Thank you again for choosing Comfy.\n\n We appreciate your business and look forward to serving you in the future.\n\nBest regards,\nComfy";


        try {
            this.emailService.sendEmailWithAttachment(customer.getEmail(), "Thank You for Your Order from Comfy!", message,"C:\\Users\\HP\\homeDecor\\email\\logo.jpg");
        } catch (MessagingException e) {
            System.out.println(e);
            throw new RuntimeException(e);

        }*/
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

    @Override
    public List<CommandShowDTO> displayCommand(int id) {
        Customer c = customerRepository.findById(id).orElse(null);
        List<Command> commands = commandRepository.findByCustomer(c);
        List<CommandShowDTO> commandShowDTOS = new ArrayList<>();
        for(Command command :commands){
            CommandShowDTO commandShowDTO = new CommandShowDTO();
            commandShowDTO.setCommandState(command.getCommandState());
            commandShowDTO.setDate(command.getDate());
            commandShowDTO.setAssemblyPrice(command.getAssemblyPrice());
            commandShowDTO.setCouponDiscount(command.getCouponDiscount());
            commandShowDTO.setDeliveryDate(command.getDeliveryDate());
            commandShowDTO.setDeliveryPrice(command.getDeliveryPrice());
            commandShowDTO.setEmail(command.getEmail());
            commandShowDTO.setPaymentMethod(command.getPaymentMethod());
            commandShowDTO.setPhone(command.getPhone());
            commandShowDTO.setTotalPrice(command.getTotalPrice());
            commandShowDTO.setWithAssembly(command.isWithAssembly());
            commandShowDTO.setRef(command.getRef());
            commandShowDTO.setName(command.getName());

            commandShowDTO.setId(command.getId());
            List<CommandItem> commandItems =  command.getCommandItems().stream().collect(Collectors.toList());
            List<ItemCommandDTO> itemDTOS = new ArrayList<>();
            for(CommandItem item : commandItems){
                ItemCommandDTO itemDTO = new ItemCommandDTO();
                itemDTO.setPrice(item.getPrice());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setProduct(productMapper.fromProduct(item.getProduct()));
                itemDTOS.add(itemDTO);

            }


            commandShowDTO.setCommandItems(itemDTOS);
            commandShowDTO.setDeliveryAdress(command.getDeliveryAdress());
            commandShowDTOS.add(commandShowDTO);
        }
        return commandShowDTOS;
    }

    @Override
    public List<ItemCommandDTO> displayCommandItems(int idCommande) {
        Command c = commandRepository.findById(idCommande).orElse(null);
        List<CommandItem> commandItems =  c.getCommandItems().stream().collect(Collectors.toList());
        List<ItemCommandDTO> itemDTOS = new ArrayList<>();
        for(CommandItem item : commandItems){
            ItemCommandDTO itemDTO = new ItemCommandDTO();
            itemDTO.setPrice(item.getPrice());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setProduct(productMapper.fromProduct(item.getProduct()));
            itemDTOS.add(itemDTO);

        }
        return itemDTOS;
    }

}
