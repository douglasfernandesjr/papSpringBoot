package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.model.request.ClientCreateRequest;
import com.example.demo.service.ClientService;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
	public ModelAndView findAll() {
		
		ModelAndView mv = new ModelAndView("/client");
		mv.addObject("clients", clientService.findAll());
		
		return mv;
    }
    
    @GetMapping("/client/add")
	public ModelAndView add(ClientCreateRequest client) {
		
		ModelAndView mv = new ModelAndView("/clientAdd");
		mv.addObject("client", client);
		
		return mv;
	}


    @PostMapping("/client/save")
	public ModelAndView save(@Valid ClientCreateRequest client, BindingResult result) {
		
		if(result.hasErrors()) {
			return add(client);
		}
		
		clientService.createNew(client);
		
		return findAll();
	}
}