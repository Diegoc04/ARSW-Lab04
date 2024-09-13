/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 *
 * @author hcadavid
 */

@Component("inMemoryBlueprintsPersistence")
public class InMemoryBlueprintPersistence {

    private final Map<String, Blueprint> blueprintsByName = new HashMap<>();
    private final Map<String, Set<Blueprint>> blueprintsByAuthor = new HashMap<>();

    /**
     * Guarda un blueprint en memoria.
     * @param blueprint El blueprint a guardar.
     * @throws BlueprintPersistenceException Si ocurre un error al guardar el blueprint.
     */
    public void saveBlueprint(Blueprint blueprint) throws BlueprintPersistenceException {
        String key = blueprint.getAuthor() + ":" + blueprint.getName();
        
        if (blueprintsByName.containsKey(key)) {
            throw new BlueprintPersistenceException("Blueprint with this name and author already exists.");
        }
        
        blueprintsByName.put(key, blueprint);
        
        blueprintsByAuthor.computeIfAbsent(blueprint.getAuthor(), k -> new HashSet<>()).add(blueprint);
    }

    /**
     * Obtiene un blueprint dado el autor y el nombre.
     * @param author El autor del blueprint.
     * @param name El nombre del blueprint.
     * @return El blueprint correspondiente.
     * @throws BlueprintNotFoundException Si el blueprint no se encuentra.
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        Blueprint blueprint = blueprintsByName.get(author + ":" + name);
        if (blueprint == null) {
            throw new BlueprintNotFoundException("Blueprint not found.");
        }
        return blueprint;
    }

    /**
     * Obtiene todos los blueprints de un autor dado.
     * @param author El autor de los blueprints.
     * @return Un conjunto de blueprints del autor.
     * @throws BlueprintNotFoundException Si el autor no tiene blueprints.
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        Set<Blueprint> blueprints = blueprintsByAuthor.get(author);
        if (blueprints == null || blueprints.isEmpty()) {
            throw new BlueprintNotFoundException("No blueprints found for the author.");
        }
        return blueprints;
    }
}
