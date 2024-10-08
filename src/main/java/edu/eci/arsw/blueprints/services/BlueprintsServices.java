/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import edu.eci.arsw.blueprints.persistence.impl.Filter.BlueprintFilter;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */

@Service
public class BlueprintsServices {

    @Autowired
    private BlueprintsPersistence bpp;

    //Inyectamos el filtro usando @Qualifier. se puede cambiar entre "redundancyFilter" o "subsamplingFilter" dependiendo de cual se necesite.
    @Autowired
    @Qualifier("redundancyFilter")  // Cambiar a "subsamplingFilter" si se necesita el otro filtro
    private BlueprintFilter blueprintFilter;

    /**
     * Adds a new blueprint to the system.
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);  // Delegates the saving logic to the persistence layer
    }

    /**
     * Retrieves all blueprints available.
     * 
     * @return a set of all blueprints
     */
    public Set<Blueprint> getAllBlueprints() {
        return bpp.getAllBlueprints();  // Retrieve all blueprints from persistence
    }

    /**
     * Retrieves the blueprint by author and name.
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author, after applying the filter
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint bp = bpp.getBlueprint(author, name);  
        // Aplica el filtro antes de devolver el blueprint
        return blueprintFilter.filter(bp);  
    }

    /**
     * Retrieves all blueprints by a specific author.
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author, after applying the filter
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = bpp.getBlueprintsByAuthor(author);  
        // Aplica el filtro a cada blueprint antes de devolverlos
        return blueprints.stream().map(blueprintFilter::filter).collect(Collectors.toSet());
    }
}
