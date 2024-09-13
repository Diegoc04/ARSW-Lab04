package edu.eci.arsw.springdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //bean que puede ser inyectado
public class GrammarChecker {

    @Autowired //Inyecta automáticamente el bean de tipo SpellChecker
    private SpellChecker sc; //El campo sc se inyectará automáticamente

    public SpellChecker getSpellChecker() {
        return sc;
    }

    public String check(String text) {
        StringBuffer sb = new StringBuffer();
        sb.append("Spell checking output: " + sc.checkSpell(text));
        sb.append("Plagiarism checking output: Not available yet");
        return sb.toString();
    }
}

