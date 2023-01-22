package ru.otus.professional.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.professional.controller.dto.ClientDto;
import ru.otus.professional.model.Client;
import ru.otus.professional.service.ClientService;
import ru.otus.professional.utils.ObjectMapperUtils;

@Controller
@RequiredArgsConstructor
public class ClientFormController {

    private static final String CLIENTS_DEFAULT_PAGE = "/";

    private final ClientService clientService;
    private final ObjectMapperUtils mapper;

    @GetMapping({"/", "/clients"})
    public String getAllClients(Model model) {
        var bookDtoList = mapper.mapAll(clientService.getAll(), ClientDto.class);
        model.addAttribute("clients", bookDtoList);
        return "clients";
    }

    @PostMapping("/client/create")
    public String createFormForBook(@ModelAttribute ClientDto clientDto) {
        clientService.createClient(mapper.map(clientDto, Client.class));
        return "redirect:" + CLIENTS_DEFAULT_PAGE;
    }

    @PostMapping("/client/delete")
    public String deleteClient(@RequestParam("id") long id) {
        clientService.removeClientById(id);
        return "redirect:" + CLIENTS_DEFAULT_PAGE;
    }

    @GetMapping("/client/create")
    public String formForCreateClient(Model model) {
        model.addAttribute("client", new ClientDto());
        return "create-client";
    }
}
