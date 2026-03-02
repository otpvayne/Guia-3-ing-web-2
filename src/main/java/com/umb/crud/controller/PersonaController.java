/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.validation.Valid
 *  org.springframework.stereotype.Controller
 *  org.springframework.ui.Model
 *  org.springframework.validation.BindingResult
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.ModelAttribute
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.umb.crud.controller;

import com.umb.crud.model.Persona;
import com.umb.crud.repository.PersonaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/personas"})
public class PersonaController {
    private final PersonaRepository repo;

    public PersonaController(PersonaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("personas", (Object)this.repo.findAll());
        return "listar";
    }

    @GetMapping(value={"/nuevo"})
    public String nuevo(Model model) {
        model.addAttribute("persona", (Object)new Persona());
        return "form";
    }

    @PostMapping
    public String crear(@Valid @ModelAttribute Persona persona, BindingResult br) {
        if (br.hasErrors()) {
            return "form";
        }
        this.repo.save(persona);
        return "redirect:/personas";
    }

    @GetMapping(value={"/{id}/editar"})
    public String editar(@PathVariable Long id, Model model) {
        Persona p = (Persona)this.repo.findById(id).orElseThrow();
        model.addAttribute("persona", (Object)p);
        return "form";
    }

    @PostMapping(value={"/{id}"})
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute Persona persona, BindingResult br) {
        if (br.hasErrors()) {
            return "form";
        }
        persona.setId(id);
        this.repo.save(persona);
        return "redirect:/personas";
    }

    @PostMapping(value={"/{id}/eliminar"})
    public String eliminar(@PathVariable Long id) {
        this.repo.deleteById(id);
        return "redirect:/personas";
    }
}

