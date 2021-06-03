package cz.czechitas.java2webapps.lekce9.repository;

import cz.czechitas.java2webapps.lekce9.entity.Osoba;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


// Repository pro tabulku {@code osoba}.

@Repository
public interface OsobaRepository extends JpaRepository<Osoba, Long> {

    /**
     * Vyhledá všechny osoby s adresou v dané obci.
     * dotaz je napsany v JPQL (java persistand query languae)
     * SELECT -> klicove slovo
     * JOIN -> spojuje dve tabulky
     */

    @Query("SELECT o FROM Osoba o JOIN o.adresa a WHERE a.obec = ?1")
    Page<Osoba> findByObec(String obec, Pageable pageable);


    /**
     * Vyhledá všechny osoby, které se narodily v daný den nebo dříve.
     * findBy -> hledej na zaklade zadaneho parametru
     * DatumNarozeni -> sloupecek, ve kterem bude hledat
     * Before -> jakym zpusobem se bude hledat, hledat pred mnou zadanym parametrem
     * LocalDate datum -> jaky parametr se hleda
     * Pageable pageable -> interface, nese informaci na kolikate jsem strance, kolik zaznamu se ma na strance zobrazit
     * Page -> Spring nevraci klasicky Lisst, ale vraci interface Page <- chova se jako List, mohu cist jednotlive zaznamy +
     * info na jake jsem strance, pocet zaznamu na strance, kolik zaznamu je celkem
     */

    Page<Osoba> findByDatumNarozeniBefore(LocalDate datum, Pageable pageable);


    /**
     * Vyhledá všechny osoby, jejichž příjmení začíná na uveddený text.
     * findBy -> hledej na zaklade zadaneho parametru
     * Prijmeni -> sloupecek, ve kterem bude hledat
     * StartingWith -> jakym zpusobem se bude hledat
     * IgnoreCase -> nerozlisuj velka a mala pismena
     * String prijmeni -> jaky parametr se hleda
     * Page -> Spring nevraci klasicky Lisst, ale vraci interface Page <- chova se jako List, mohu cist jednotlive zaznamy +
     * info na jake jsem strance, pocet zaznamu na strance, kolik zaznamu je celkem
     */

    Page<Osoba> findByPrijmeniStartingWithIgnoreCase(String prijmeni, Pageable pageable);


    //   Vyhledá všechny osoby, které se narodily v rozmezí zadaných let.
    @Query("SELECT o FROM Osoba o WHERE YEAR(o.datumNarozeni) BETWEEN :pocatecniRok AND :koncovyRok")
    Page<Osoba> findByRok(@Param("pocatecniRok") int pocatecniRok, @Param("koncovyRok") int koncovyRok, Pageable pageable);
}
