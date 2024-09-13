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

    /**
     * Prueba la funcionalidad de guardar y cargar un nuevo blueprint.
     * 
     * Este test verifica que un blueprint recién guardado se puede cargar correctamente 
     * desde la persistencia en memoria. Se asegura de que el blueprint cargado no sea null
     * y que sea el mismo que el que se guardó.
     * 
     * @throws BlueprintPersistenceException si ocurre un error al guardar el blueprint.
     * @throws BlueprintNotFoundException si ocurre un error al recuperar el blueprint.
     */
    @Test
    public void guardarYCargarNuevoBlueprintTest() throws BlueprintPersistenceException, BlueprintNotFoundException {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        Point[] pts0 = new Point[]{new Point(40, 40), new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint", pts0);

        ibpp.saveBlueprint(bp0);

        Point[] pts = new Point[]{new Point(0, 0), new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint", pts);

        ibpp.saveBlueprint(bp);

        // Verifica que el blueprint se ha cargado correctamente
        assertNotNull("Cargar un blueprint previamente almacenado devolvió null.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()));

        // Verifica que el blueprint cargado es el mismo que el guardado
        assertEquals("Cargar un blueprint previamente almacenado devolvió un blueprint diferente.", ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
    }

    /**
     * Prueba la funcionalidad de guardar un blueprint existente con el mismo nombre y autor.
     * 
     * Este test verifica que se lanza una excepción al intentar guardar un segundo blueprint
     * con el mismo nombre y autor que un blueprint ya existente. Esto asegura que la persistencia
     * no permita duplicados de blueprints con el mismo nombre y autor.
     */
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
            // Intento de guardar un blueprint con el mismo nombre y autor
            ibpp.saveBlueprint(bp2);
            fail("Se esperaba una excepción después de guardar un segundo blueprint con el mismo nombre y autor.");
        } catch (BlueprintPersistenceException ex) {
            // Excepción esperada
        }
    }

    /**
     * Prueba la funcionalidad de obtener blueprints por autor.
     * 
     * Este test verifica que se pueden recuperar todos los blueprints asociados a un autor específico.
     * Se asegura de que el número de blueprints recuperados y los blueprints en sí sean correctos
     * para el autor dado.
     * 
     * @throws BlueprintPersistenceException si ocurre un error al guardar el blueprint.
     * @throws BlueprintNotFoundException si ocurre un error al recuperar el blueprint.
     */
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

        // Verifica el número de blueprints recuperados para el autor
        assertEquals("Número de blueprints para el autor 'john' es incorrecto.", 2, blueprints.size());
        // Verifica que los blueprints correctos están en el conjunto
        assertTrue("El blueprint 'thepaint1' debería estar en el conjunto.", blueprints.contains(bp1));
        assertTrue("El blueprint 'thepaint2' debería estar en el conjunto.", blueprints.contains(bp2));
    }

    /**
     * Prueba la funcionalidad de obtener blueprints por un autor no existente.
     * 
     * Este test verifica que se lanza una excepción al intentar obtener blueprints de un autor
     * que no existe en la persistencia. Esto asegura que la búsqueda de blueprints por autor maneje
     * correctamente los casos en los que el autor no tiene blueprints.
     */
    @Test
    public void obtenerBlueprintPorAutorNoExistenteTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();

        try {
            // Intento de obtener blueprints de un autor no existente
            ibpp.getBlueprintsByAuthor("noexistente");
            fail("Se esperaba una excepción al consultar blueprints de un autor no existente.");
        } catch (BlueprintNotFoundException ex) {
            // Excepción esperada
        }
    }
}
