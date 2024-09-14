package edu.eci.arsw.blueprints.persistence.impl.Filter;

import edu.eci.arsw.blueprints.model.Blueprint;

public interface BlueprintFilter {
    Blueprint apply(Blueprint blueprint);
}

