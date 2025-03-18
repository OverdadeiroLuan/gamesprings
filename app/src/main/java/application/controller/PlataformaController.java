package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import application.model.Plataforma;
import application.repository.PlataformaRepository;


@Controller
@RequestMapping("/plataforma")
public class PlataformaController {

    @Autowired
    private PlataformaRepository plataformaRepo;

    @RequestMapping("/list")
    public String listPlataformas(Model ui) {
        ui.addAttribute("plataformas", plataformaRepo.findAll());
        return "plataforma/list"; // Nome da view para listar as plataformas
    }

    @RequestMapping("/insert")
    public String addPlataformaForm(Model ui) {
        return "plataformas/insert"; // Nome da view para o formulário de adição
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(@RequestParam("nome")String nome) {
        Plataforma plataforma = new Plataforma();
        plataformaRepo.save(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após adicionar
    }

    @RequestMapping("/edit/{id}")
    public String update(@RequestParam("id") long id, Model model) {
        Plataforma plataforma = plataformaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de plataforma inválido:" + id));
        model.addAttribute("plataforma", plataforma);
        return "plataformas/edit"; // Nome da view para o formulário de edição
    }

    @RequestMapping("/update/{id}")
    public String update(@RequestParam("id") long id, @RequestParam Plataforma plataforma) {
        plataforma.setId(id); // Garante que estamos atualizando a plataforma correta
        plataformaRepo.save(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após atualizar
    }

    @RequestMapping("/delete/{id}")
    public String delete(@RequestParam("id") long id) {
        Plataforma plataforma = plataformaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de plataforma inválido:" + id));
        plataformaRepo.delete(plataforma);
        return "redirect:/plataformas/list"; // Redireciona para a lista após excluir
    }
}