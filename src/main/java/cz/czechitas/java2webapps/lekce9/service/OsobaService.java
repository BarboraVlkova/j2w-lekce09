package cz.czechitas.java2webapps.lekce9.service;

import cz.czechitas.java2webapps.lekce9.entity.Osoba;
import cz.czechitas.java2webapps.lekce9.repository.OsobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


//  Služba pro práci s osobami a adresami.
@Service
public class OsobaService {
    private final OsobaRepository osobaRepository;


    @Autowired
    public OsobaService(OsobaRepository osobaRepository) {
        this.osobaRepository = osobaRepository;
    }


//  Vrací stránkovaný seznam všech osob v databázi.
    public Page<Osoba> seznamOsob(Pageable pageable) {
        return osobaRepository.findAll(pageable);
    }


//  vraci strankovany seznam vsech osob,ktere se narodili mezi uvedenumi roky
public Page<Osoba> seznamDleRokuNarozeni (int rokOd, int rokDo, Pageable pageable){
        return osobaRepository.findByRok(rokOd, rokDo, pageable);
}


//  vraci strankovany seznam vsech osob, jejichz prijmeni zacina uvedenym textem
    public Page<Osoba> seznamDlePrijmeni(String prijmeni, Pageable pageable) {
        return osobaRepository.findByPrijmeniStartingWithIgnoreCase(prijmeni, pageable);
    }


//  vraci strankovany seznam vsech osob v databazi, ktere byli v uvedene obci
    public Page<Osoba> seznamDleObce(String obec, Pageable pageable) {
        return osobaRepository.findByObec(obec, pageable);
    }


//  vraci strankovany seznam vsech osob, ktere dosahli zadaneho veku
public Page<Osoba> seznamDleVeku(int vek, Pageable pageable) {
    LocalDate date = LocalDate.now().minusYears(vek);
    return osobaRepository.findByDatumNarozeniBefore(date, pageable);
}


}
