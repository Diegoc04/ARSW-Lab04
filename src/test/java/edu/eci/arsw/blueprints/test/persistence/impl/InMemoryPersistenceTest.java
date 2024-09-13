/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {

    @Test
    public void guardarYCargarNuevoBlueprintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        assertNotNull("Cargar un blueprint previamente almacenado devolvió null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        assertEquals("Cargar un blueprint previamente almacenado devolvió un blueprint diferente.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
    }

    @Test
    public void guardarBlueprintExistenteTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Fallo al persistir el blueprint al insertar el primer blueprint.");
        }

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint", pts2);

        try {
            ibpp.saveBlueprint(bp2);
            fail("Se esperaba una excepción después de guardar un segundo blueprint con el mismo nombre y autor.");
        } catch (BlueprintPersistenceException ex) {
            // Excepción esperada
        }
    }

    @Test
    public void obtenerBlueprintPorAutorTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts1 = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint1", pts1);

        Point[] pts2 = new Point[]{new Point(10, 10), new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint2", pts2);

        ibpp.saveBlueprint(bp1);
        ibpp.saveBlueprint(bp2);

        Set<Blueprint> blueprints = ibpp.getBlueprintsByAuthor("john");

        assertEquals("Número de blueprints para el autor 'john' es incorrecto.", 2, blueprints.size());
        assertTrue("El blueprint 'thepaint1' debería estar en el conjunto.", blueprints.contains(bp1));
        assertTrue("El blueprint 'thepaint2' debería estar en el conjunto.", blueprints.contains(bp2));
    }

    @Test
    public void obtenerBlueprintPorAutorNoExistenteTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        try {
            ibpp.getBlueprintsByAuthor("noexistente");
            fail("Se esperaba una excepción al consultar blueprints de un autor no existente.");
        } catch (BlueprintNotFoundException ex) {
            // Excepción esperada
        }
    }
}
