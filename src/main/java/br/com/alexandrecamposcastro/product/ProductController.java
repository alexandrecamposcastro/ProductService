package br.com.alexandrecamposcastro.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.alexandrecamposcastro.product.model.ProductEntity;
import br.com.alexandrecamposcastro.product.repository.ProductRepository;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/")
    public String index(Model model, @RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("products", repository.findByNameContainingIgnoreCase(search));
        } else {
            model.addAttribute("products", repository.findAll());
        }
        model.addAttribute("newProduct", new ProductEntity());
        return "products";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute ProductEntity product) {
        repository.save(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        ProductEntity product = repository.findById(id).orElse(new ProductEntity());
        model.addAttribute("products", repository.findAll());
        model.addAttribute("newProduct", product);
        return "products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }
}