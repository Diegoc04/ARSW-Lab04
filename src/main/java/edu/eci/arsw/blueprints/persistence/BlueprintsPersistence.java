/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.Set;

import org.springframework.stereotype.Repository;

/**
 *
 * @author hcadavid
 */
@Repository
public interface BlueprintsPersistence {

    /**
     * Saves a new blueprint.
     * 
     * @param bp the new blueprint
     * @throws BlueprintPersistenceException if a blueprint with the same name already exists,
     * or any other low-level persistence error occurs.
     */
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     * Retrieves the blueprint by author and name.
     * 
     * @param author blueprint's author
     * @param bprintname blueprint's name
     * @return the blueprint of the given name and author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException;

    /**
     * Retrieves all blueprints by a specific author.
     * 
     * @param author blueprint's author
     * @return a set of blueprints by the given author
     * @throws BlueprintNotFoundException if there is no blueprint by that author
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    /**
     * Retrieves all blueprints in the system.
     * 
     * @return a set of all blueprints
     */
    public Set<Blueprint> getAllBlueprints();
}

