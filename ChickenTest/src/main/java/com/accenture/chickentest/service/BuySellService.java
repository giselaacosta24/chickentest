package com.accenture.chickentest.service;

import com.accenture.chickentest.domain.dao.Chicken;
import com.accenture.chickentest.domain.dao.Egg;
import com.accenture.chickentest.domain.dao.Farm;
import com.accenture.chickentest.domain.dao.Transaction;
import com.accenture.chickentest.domain.dto.ChickenDTO;
import com.accenture.chickentest.domain.dto.EggDTO;
import com.accenture.chickentest.domain.dto.FarmDTO;
import com.accenture.chickentest.domain.dto.ParametroDTO;
import com.accenture.chickentest.domain.enumStatus.Status;
import com.accenture.chickentest.exception.ObjectNotFoundException;
import com.accenture.chickentest.mapper.ModelMapper;
import com.accenture.chickentest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
@Service
public class BuySellService {


    private final ChickenRepository chickenRepository;
    private final EggRepository eggRepository;
    private final FarmRepository farmRepository;

    private final TransactionRepository transactionRepository;

    private final ParametroRepository parametroRepository;
    private Long capacityFarm;
    private Long countFarm;
    @Autowired
    private JavaMailSender emailSender;
@Autowired
    private FarmService farmService;

    public void setMailSender(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }
    public BuySellService(ChickenRepository chickenRepository, EggRepository eggRepository,TransactionRepository transactionRepository,ParametroRepository parametroRepository, FarmRepository farmRepository) {
        this.chickenRepository = chickenRepository;
        this.eggRepository = eggRepository;
        this.transactionRepository = transactionRepository;
        this.parametroRepository = parametroRepository;
        this.farmRepository=farmRepository;

    }

    public ResponseEntity<ChickenDTO> buyChicken(ChickenDTO chickenDTO,Long id) {
        FarmDTO farmDTO=farmService.getFarm(8);
        List<ChickenDTO> chickens=chickenRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOChicken)
                .collect(Collectors.toList());
        List<ChickenDTO> chickenswithfarm= new ArrayList<>();
        chickens.forEach(c -> {
            if(Objects.equals(c.getIdFarm(), id) && ((c.getStatus() == Status.COMPRADO)||(c.getStatus() == Status.CONVERTIDO) ))
            {

                chickenswithfarm.add(c);
            }
        });
        countFarm=chickenswithfarm.stream().count();
        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CapacidadMaximaGranja")) {
                {
                    capacityFarm = p.getValor();

                }
            }

        });
        if (countFarm < capacityFarm) {

            chickenDTO.setIdFarm(id);
            chickenDTO.setStatus(Status.COMPRADO);

            Chicken chicken = ModelMapper.INSTANCE.DTOtoDaoChicken(chickenDTO);

            Transaction transaction = new Transaction();

            transaction.setPrice(chickenDTO.getPrice());
            transaction.setTypeProduct("Chicken");
            transaction.setTypeTransaction("Compra");
            transaction.setDateTransaction(new Date());
            transactionRepository.save(transaction);
            chickenRepository.save(chicken);
            farmService.updateAmount("compra",farmDTO.getId(),farmDTO,chicken.getPrice());
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            System.out.println("enviando correo");

            try {

                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

                message.setSubject("Capacidad de granja limitada");
                helper = new MimeMessageHelper(message, true);
                helper.setFrom("giseacosta651@gmail.com");
                helper.setTo("giseacosta651@gmail.com");
                String htmlMsg = "<h3>Detalle Stock:</h3>"+
                        "<h3> Pollos:</h3>"+countFarm;

                helper.setText(htmlMsg, true);
                emailSender.send(message);
            } catch (MessagingException ex) {
                System.out.println("Ocurrio un error al enviar correo");
            }
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        }
    }


    public ResponseEntity<EggDTO> buyEgg(EggDTO eggDTO, Long id) {
        FarmDTO farmDTO=farmService.getFarm(8);

        List<EggDTO> eggs=eggRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOEgg)
                .collect(Collectors.toList());
        List<EggDTO> eggswithfarm= new ArrayList<>();
        eggs.forEach(e -> {
            if(Objects.equals(e.getIdFarm(), id)  && ((e.getStatus() == Status.COMPRADO) ||(e.getStatus() == Status.PUESTO)))
            {

                eggswithfarm.add(e);
            }
        });

        countFarm=eggswithfarm.stream().count();
        List<ParametroDTO> parametros = parametroRepository.findAll().stream().map(ModelMapper.INSTANCE::daoToDTOParametro)
                .collect(Collectors.toList());

        parametros.forEach(p -> {
            if (Objects.equals(p.getClave(), "CapacidadMaximaGranja")) {
                {
                    capacityFarm = p.getValor();

                }
            }

        });

        if (countFarm < capacityFarm) {

            eggDTO.setIdFarm(id);
            eggDTO.setStatus(Status.COMPRADO);
            Egg egg = ModelMapper.INSTANCE.DTOtoDaoEgg(eggDTO);

            Transaction transaction = new Transaction();
            transaction.setPrice(eggDTO.getPrice());
            transaction.setTypeProduct("Egg");
            transaction.setTypeTransaction("Compra");
            transaction.setDateTransaction(new Date());
            transactionRepository.save(transaction);
            farmService.updateAmount("compra",farmDTO.getId(),farmDTO,egg.getPrice());

            eggRepository.save(egg);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else {
            System.out.println("enviando correo");

            try {

                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

                message.setSubject("Capacidad de granja limitada");
                helper = new MimeMessageHelper(message, true);
                helper.setFrom("giseacosta651@gmail.com");
                helper.setTo("giseacosta651@gmail.com");
                String htmlMsg = "<h3>Detalle Stock:</h3>"+
                        "<h3> Huevos:</h3>"+countFarm;

                helper.setText(htmlMsg, true);
                emailSender.send(message);
            } catch (MessagingException ex) {
                System.out.println("Ocurrio un error al enviar correo");
            }
            return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

        }
    }

    public ResponseEntity<ChickenDTO>  sellChicken(long id) {
        Chicken chicken = chickenRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));

        Transaction transaction=new Transaction();
        transaction.setPrice(chicken.getPrice());
        transaction.setTypeProduct("Chicken");
        transaction.setTypeTransaction("Venta");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);

        chicken.setStatus(Status.VENDIDO);
        chickenRepository.save(chicken);

        return  new ResponseEntity<>(HttpStatus.OK);

    }

    public ResponseEntity<EggDTO>  sellEgg(long id) {
        Egg egg = eggRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("No existe id seleccionado, no se puede eliminar"));
        Transaction transaction=new Transaction();

        transaction.setPrice(egg.getPrice());
        transaction.setTypeProduct("Egg");
        transaction.setTypeTransaction("Venta");
        transaction.setDateTransaction(new Date());
        transactionRepository.save(transaction);

        egg.setStatus(Status.VENDIDO);
        eggRepository.save(egg);
        return  new ResponseEntity<>(HttpStatus.OK);

    }
}
