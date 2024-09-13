/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.springdemo.ui;

import edu.eci.arsw.springdemo.GrammarChecker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author hcadavid
 */
public class Main {

    public static void main(String[] args) {
        //Cargar el contexto de Spring desde el archivo applicationContext.xml
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        //Obtener el bean GrammarChecker del contexto de Spring
        GrammarChecker gc = ac.getBean(GrammarChecker.class);
        
        //Usar el GrammarChecker para verificar el texto
        System.out.println(gc.check("la la la "));
    }
}
