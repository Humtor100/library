package com.kirito.library.controller.view;

import com.kirito.library.dto.PersonRequest;
import com.kirito.library.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/people")
@RequiredArgsConstructor
public class PeopleViewController {

    private final PersonService personService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("people", personService.getAllPersons());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new PersonRequest(null, null));
        return "people/new";
    }

    @PostMapping
    public String createPerson(
            @Valid @ModelAttribute("person") PersonRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "people/new";
        }

        personService.createPerson(request);
        return "redirect:/people";
    }

    @GetMapping("/{personId}")
    public String showPerson(@PathVariable UUID personId, Model model) {
        model.addAttribute("person", personService.getPersonOrThrow(personId));
        return "people/show";
    }

    @GetMapping("/{personId}/edit")
    public String editPerson(@PathVariable UUID personId, Model model) {
        model.addAttribute("personId", personId);
        model.addAttribute("person", personService.getPersonOrThrow(personId));
        return "people/edit";
    }

    @PostMapping("/{personId}/edit")
    public String updatePerson(
            @PathVariable UUID personId,
            @Valid @ModelAttribute("person") PersonRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("personId", personId);
            return "people/edit";
        }

        personService.updatePerson(personId, request);
        return "redirect:/people/" + personId;
    }

    @PostMapping("/{personId}/delete")
    public String deletePerson(@PathVariable UUID personId) {
        personService.deletePerson(personId);
        return "redirect:/people";
    }
}