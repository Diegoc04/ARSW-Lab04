package edu.eci.arsw.blueprints.persistence.impl.Filter;

import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.model.Blueprint;

public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        
        for (int i = 0; i < points.size(); i += 2) {
            filteredPoints.add(points.get(i));
        }
        
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), filteredPoints.toArray(new Point[0]));
    }
}

