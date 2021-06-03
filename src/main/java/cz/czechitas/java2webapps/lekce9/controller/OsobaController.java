package cz.czechitas.java2webapps.lekce9.controller;

import cz.czechitas.java2webapps.lekce9.service.OsobaService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller pro zobrazování seznamů osob.
 */
@Controller
public class OsobaController {
    private final OsobaService service;

    public OsobaController(OsobaService service) {
        this.service = service;
    }


    /**
     * nastaveno serazeni podle prijmeni a jmena
     * sort = {"prijmeni", "jmeno"} = parametry zadane v entity/Osoba
     * tento controller vola service -> service.seznamOsob(pageable)
     */
    @GetMapping("/")
    public ModelAndView zakladniSeznam(@PageableDefault(sort = {"prijmeni", "jmeno"}) Pageable pageable) {
        return new ModelAndView("osoby")
                .addObject("osoby", service.seznamOsob(pageable));
    }


//    1   Sařezané dle data narození
    @GetMapping("/dle-data-nerozeni")
    public ModelAndView dleDataNarozeni(@PageableDefault(sort = {"datumNarozeni", "prijmeni"}) Pageable pageable) {
        return new ModelAndView("osoby")
                .addObject("osoby", service.seznamOsob(pageable));
    }


//  2     Podle roku narození
    @GetMapping("/rok-narozeni")
    public ModelAndView rokNarozeni(@RequestParam("od") int rokOd, @RequestParam ("do") int rokDo, @PageableDefault(sort = {"datumNarozeni", "prijmeni"}) Pageable pageable){
        return new ModelAndView("osoby")
                .addObject("osoby", service
                        .seznamDleRokuNarozeni(rokOd, rokDo, pageable));
    }



//  3       Podle prijmeni
@GetMapping("/prijmeni")
public ModelAndView prijmeni(String prijmeni, @PageableDefault() Pageable pageable) {
    return new ModelAndView("osoby")
            .addObject("osoby", service.seznamDlePrijmeni(prijmeni, pageable));
}


//   4       Podle obce
    @GetMapping("/obec")
    public ModelAndView obec(String obec, @PageableDefault(sort = {"prijmeni", "jmeno"}) Pageable pageable) {
        return new ModelAndView("osoby-s-adresou")
                .addObject("osoby", service.seznamDleObce(obec, pageable));
    }


//   5   Podle minimalniho veku
    @GetMapping("/minimalni-vek")
    public ModelAndView minimalniVek(int vek, @PageableDefault(sort = {"prijmeni", "jmeno"}) Pageable pageable) {
        return new ModelAndView("osoby")
                .addObject("osoby", service.seznamDleVeku(vek, pageable));
    }


    @GetMapping("/vyber")
    public String vyber() {
        return "vyber";
    }

}