package ma.org.comfybackend.security.Services;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import ma.org.comfybackend.security.DTO.*;
import ma.org.comfybackend.security.Enumerations.CommandState;
import ma.org.comfybackend.security.Mappers.*;
import ma.org.comfybackend.security.Repositories.*;
import org.apache.commons.lang3.RandomStringUtils;
import ma.org.comfybackend.security.Entities.*;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class CommandServiceImpl implements CommandService{

    RegionRepository regionRepository;
    CityRepository cityRepository;
    CustomerRepository customerRepository;
    EmailService emailService;
    private ItemCommandMapper itemMapper;
    private CustomerRegisterMapper customerRegisterMapper;

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
        this.customerRegisterMapper = new CustomerRegisterMapper();
        this.itemMapper = new ItemCommandMapper();
    }

    @Override
    public List<Region> listRegions() {
        return regionRepository.findAll();
    }

    @Override
    public List<City> listCities(int idR) {
        System.out.println("lolo");
        Region r = regionRepository.findById(idR).orElse(null);
        System.out.println(r.getNom());
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
                commandItem.setPrice(item.getProduct().getPrice()-item.getProduct().getPromotion());
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

       /*String message = "Dear " + customer.getName() + ",\n\nThank you for choosing Decor for your furniture needs. We appreciate your recent order and we are honored to have the opportunity to provide you with stylish and comfortable furniture for your home.\n\n" +
                "We want you to know that your order has been received and is being prepared for shipment. Our team is dedicated to ensuring that your order is carefully packaged and delivered to you as quickly as possible. Your order is expected to be delivered to you by " + c.getDeliveryDate().toString() + ". We will also keep you informed about the status of your order and provide you with a tracking number so you can monitor its progress.\n\n" +
                "If you have any questions or concerns about your order, please do not hesitate to contact our customer service team. We are always here to help and ensure that your experience with Decor is nothing but positive.\n\n" +
                "Thank you again for choosing Decor.\n\n We appreciate your business and look forward to serving you in the future.\n\nBest regards,\nDecor";


        try {
            this.emailService.sendEmailWithAttachment(customer.getEmail(), "Thank You for Your Order from Decor!", message,"C:\\Users\\HP\\homeDecor\\email\\logo.jpg");
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

    @Override
    public ByteArrayInputStream exportPDF(int idCommande) {

        Command command = commandRepository.findById(idCommande).orElse(null);
      Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter writer=  PdfWriter.getInstance(document,out);
            document.open();

            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD,25, BaseColor.WHITE);
            com.itextpdf.text.Font font2 = FontFactory.getFont(FontFactory.COURIER_OBLIQUE,16, BaseColor.WHITE);


            // Create table to hold content
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(100);



            // Create cell to hold content and set background color to salmon
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(new BaseColor(255,153,102));
            cell.setBorder(Rectangle.NO_BORDER);

            cell.setPadding(20);
            cell.setLeading(0, 1);


            Image image = Image.getInstance("C:\\Users\\HP\\homeDecor\\pdf\\logo.jpg"); // Replace with the path to your image file
            image.scaleAbsolute(150f, 100f);
            image.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(image);

            Paragraph para = new Paragraph("Invoice",font);
            para.setIndentationLeft(20);

            cell.addElement(para);




           // document.add(Chunk.NEWLINE);
            Paragraph date = new Paragraph(command.getDate().toString(),font2);
            para.setIndentationLeft(25);
            cell.addElement(date);

        // Add cell to table
            table.addCell(cell);


            // Add table to document
            document.add(table);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            Paragraph paragraph = new Paragraph();
            paragraph.setSpacingBefore(20f); // Set the spacing before the paragraph to 20 points
            document.add(paragraph);

            com.itextpdf.text.Font font3 = FontFactory.getFont(null,14, BaseColor.WHITE);

            PdfPTable pdfPTable = new PdfPTable(2);
            pdfPTable.setWidthPercentage(100);
            Stream.of("Customer Information","Company").forEach( headerTitle -> {
                PdfPCell header = new PdfPCell();
                header.setLeading(0, 1);
                header.setBorder(Rectangle.NO_BORDER);

                com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA,15);

                header.setHorizontalAlignment(Element.ALIGN_LEFT);

                header.setPhrase(new Phrase(headerTitle,headFont));
                pdfPTable.addCell(header);
            });
                document.add(Chunk.NEWLINE);
                PdfPCell titleCell = new PdfPCell(new Phrase("Name: "+command.getCustomer().getName()));
                titleCell.setPaddingLeft(10);
                titleCell.setBorder(Rectangle.NO_BORDER);
                //titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable.addCell(titleCell);

                PdfPCell desCell = new PdfPCell(new Phrase("Company Name: Decor Co."));
                desCell.setPaddingLeft(10);
            desCell.setBorder(Rectangle.NO_BORDER);

            //desCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                desCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable.addCell(desCell);

            PdfPCell cell2 = new PdfPCell(new Phrase("Email: "+command.getCustomer().getEmail()));
            cell2.setPaddingLeft(10);
            cell2.setBorder(Rectangle.NO_BORDER);
            //cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell2);

            PdfPCell cell3 = new PdfPCell(new Phrase("Address: 123 Main Street, Suite 100"));
            cell3.setPaddingLeft(10);
            cell3.setBorder(Rectangle.NO_BORDER);
           // cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(new Phrase("Delivery Address: "+command.getDeliveryAdress().getAddess()));
            cell4.setPaddingLeft(10);
            cell4.setBorder(Rectangle.NO_BORDER);
           // cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell4);

            PdfPCell cell5 = new PdfPCell(new Phrase("Zip Code: 90001"));
            cell5.setPaddingLeft(10);
            cell5.setBorder(Rectangle.NO_BORDER);
          //  cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell5);

            PdfPCell cell6 = new PdfPCell(new Phrase("jbxjdhbjdcdbcd" ,font3));
            cell6.setPaddingLeft(10);
            cell6.setBorder(Rectangle.NO_BORDER);
          //  cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell6);

            PdfPCell cell7 = new PdfPCell(new Phrase("Phone Number: (555) 555-5555"));
            cell7.setPaddingLeft(10);
            cell7.setBorder(Rectangle.NO_BORDER);
          //  cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell7.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell7);

            PdfPCell cell8 = new PdfPCell(new Phrase("jbxjdhbjdcdbcd" ,font3));
            cell8.setPaddingLeft(10);
            cell8.setBorder(Rectangle.NO_BORDER);
            //  cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell8);

            PdfPCell cell9 = new PdfPCell(new Phrase("Email: info@homedecorco.com"));
            cell9.setPaddingLeft(10);
            cell9.setBorder(Rectangle.NO_BORDER);
            //  cell7.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell9.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfPTable.addCell(cell9);

            document.add(pdfPTable);

            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            PdfPTable pdfPTable2 = new PdfPTable(4);

            Stream.of("Product","Price","Quantity","Total Price").forEach( headerTitle -> {
                    PdfPCell header = new PdfPCell();
                    com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA);
                    header.setBackgroundColor(new BaseColor(255,153,102));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(headerTitle,headFont));
                pdfPTable2.addCell(header);
            });

            for(CommandItem item :command.getCommandItems()){
                PdfPCell titleCell2 = new PdfPCell(new Phrase(item.getProduct().getNom()));
                titleCell2.setPaddingLeft(1);
               // titleCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable2.addCell(titleCell2);

                PdfPCell desCell2 = new PdfPCell(new Phrase(""+item.getPrice()));
                desCell2.setPaddingLeft(1);
              //  desCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                desCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable2.addCell(desCell2);

                PdfPCell titleCell3 = new PdfPCell(new Phrase(""+item.getQuantity()));
                titleCell3.setPaddingLeft(1);
                // titleCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                titleCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable2.addCell(titleCell3);

                PdfPCell desCell4 = new PdfPCell(new Phrase(""+(item.getQuantity()*item.getPrice())));
                desCell4.setPaddingLeft(1);
                //  desCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
                desCell4.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfPTable2.addCell(desCell4);
            }




            document.add(pdfPTable2);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            com.itextpdf.text.Font fontDetail = FontFactory.getFont(FontFactory.HELVETICA,14, BaseColor.BLACK);

            Paragraph method = new Paragraph("Payment Method : "+command.getPaymentMethod().toString(),fontDetail);
            method.setAlignment(Element.ALIGN_RIGHT);
            document.add(method);


            if(command.getCouponDiscount()!=0) {
                Paragraph coupon = new Paragraph("Discount : " + command.getCouponDiscount(), fontDetail);
                coupon.setAlignment(Element.ALIGN_RIGHT);
                document.add(coupon);

            }

            String dl = (command.getDeliveryPrice()==0)? "Free Delivery": ""+command.getDeliveryPrice();

            Paragraph delevery = new Paragraph("Delivery : "+dl,fontDetail);
            delevery.setAlignment(Element.ALIGN_RIGHT);
            document.add(delevery);


            if(command.getAssemblyPrice()!=0) {
                Paragraph asseb = new Paragraph("Discount : " + command.getAssemblyPrice(), fontDetail);
                asseb.setAlignment(Element.ALIGN_RIGHT);
                document.add(asseb);

            }

            document.add(Chunk.NEWLINE);
            com.itextpdf.text.Font fonttotal = FontFactory.getFont(FontFactory.COURIER_OBLIQUE,20, new BaseColor(255,153,102));

            Paragraph total = new Paragraph("Total : "+command.getTotalPrice(),fonttotal);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);




            Image sig = Image.getInstance("C:\\Users\\HP\\homeDecor\\pdf\\signature.png"); // Replace with the path to your image file
            sig.scaleAbsolute(200f, 50f);
            sig.setAlignment(Element.ALIGN_LEFT);
            document.add(sig);


            document.close();


        } catch (DocumentException e) {
            System.out.println(e.getMessage());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public List<CommandDTO> displayAllCommands() {
        List<Command> commands = this.commandRepository.findAll();
        List<CommandDTO> collect = commands.stream().map(c -> this.commandMapper.fromCommand(c)).collect(Collectors.toList());

        for(int i=0;i<commands.size();i++){
            collect.get(i).setCustomerRegisterDTO(this.customerRegisterMapper.fromCustomer_CDTO(commands.get(i).getCustomer()));
        }
        return collect;
    }

    @Override
    public int changeState(int idCommand, String stateValue) {
        Command command = this.commandRepository.findById(idCommand).orElse(null);
        command.setCommandState(CommandState.valueOf(stateValue));
        this.commandRepository.save(command);
        return 1;
    }

    @Override
    public CommandShowDTO displayOnCommande(int id) {
        Command c = this.commandRepository.findById(id).orElse(null);
        CommandShowDTO commandShowDTO = this.commandMapper.fromCommand2(c);
        List<CommandItem> items = (List<CommandItem>) c.getCommandItems();


        List<ItemCommandDTO> collect = items.stream().map(i -> this.itemMapper.fromCommandItem(i)).collect(Collectors.toList());
       for (int i=0;i<collect.size();i++){
           collect.get(i).setProduct(this.productMapper.fromProduct(items.get(i).getProduct()));
       }

        commandShowDTO.setCommandItems(collect);

        return commandShowDTO;
    }

    @Override
    public List<DeliveryAdress> getSavedAdresses(int idCustomer) {
        Customer customer = this.customerRepository.findById(idCustomer).orElse(null);
        List<DeliveryAdress> deliveryAdresses = this.deliveryAdressRepository.findByIsSavedIsTrueAndCustomer(customer);

        return deliveryAdresses;
    }

}
