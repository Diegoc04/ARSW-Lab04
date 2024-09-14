package edu.eci.arsw.blueprints.test.service;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.impl.Filter.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.Filter.SubsamplingFilter;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Arrays;
import java.util.HashSet;

public class BlueprintsServicesTest {

    private BlueprintsServices bps;
    private RedundancyFilter redundancyFilter;
    private SubsamplingFilter subsamplingFilter;

    @Before
    public void setUp() {
        // Crear instancias reales de los filtros y del servicio
        redundancyFilter = new RedundancyFilter();
        subsamplingFilter = new SubsamplingFilter();
        bps = new BlueprintsServices();
    }

    // Prueba para añadir un nuevo blueprint
    @Test
    public void testAddNewBlueprint() {
        Blueprint bpD = new Blueprint("Daniel", "plano");
        try {
            bps.addNewBlueprint(bpD);
            Blueprint retrievedBlueprint = bps.getBlueprint("Daniel", "plano");
            assertNotNull("Blueprint should not be null", retrievedBlueprint);
            assertEquals("Blueprints should be equal", bpD, retrievedBlueprint);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Prueba para obtener un blueprint específico
    @Test
    public void testGetBlueprint() {
        Blueprint bpDiego = new Blueprint("Diego", "plano");
        try {
            bps.addNewBlueprint(bpDiego);
            Blueprint result = bps.getBlueprint("Diego", "plano");
            assertNotNull(result);
            assertEquals("Diego", result.getAuthor());
            assertEquals("plano", result.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Prueba para obtener todos los blueprints
    @Test
    public void testGetAllBlueprints() {
        try {
            Blueprint bpD = new Blueprint("Daniel", "plano");
            Blueprint bpDiego = new Blueprint("Diego", "plano");

            bps.addNewBlueprint(bpD);
            bps.addNewBlueprint(bpDiego);

            assertEquals(2, bps.getAllBlueprints().size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Prueba para obtener los blueprints de un autor específico
    @Test
    public void testGetBlueprintsByAuthor() {
        try {
            Blueprint bpD1 = new Blueprint("Daniel", "plano1");
            Blueprint bpD2 = new Blueprint("Daniel", "plano2");
            Blueprint bpDiego = new Blueprint("Diego", "plano");

            bps.addNewBlueprint(bpD1);
            bps.addNewBlueprint(bpD2);
            bps.addNewBlueprint(bpDiego);

            assertEquals(2, bps.getBlueprintsByAuthor("Daniel").size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Prueba para excepción de blueprints duplicados
    @Test(expected = Exception.class)
    public void testDuplicateBlueprintThrowsException() throws Exception {
        Blueprint bpD = new Blueprint("Daniel", "plano");
        bps.addNewBlueprint(bpD);
        bps.addNewBlueprint(bpD);  // Esto debería lanzar una excepción
    }

    
}
