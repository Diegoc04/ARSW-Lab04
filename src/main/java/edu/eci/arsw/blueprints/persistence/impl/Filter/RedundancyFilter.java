package edu.eci.arsw.blueprints.persistence.impl.Filter;

import java.util.ArrayList;
import java.util.List;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("redundancyFilter") //bean que puede ser inyectado
public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint apply(Blueprint blueprint) {
        List<Point> points = blueprint.getPoints();
        if (points.isEmpty()) {
            return new Blueprint(blueprint.getAuthor(), blueprint.getName(), new Point[0]);
        }
        
        List<Point> filteredPoints = new ArrayList<>();
        Point lastPoint = null;
        for (Point point : points) {
            if (!point.equals(lastPoint)) {
                filteredPoints.add(point);
            }
            lastPoint = point;
        }
        
        return new Blueprint(blueprint.getAuthor(), blueprint.getName(), filteredPoints.toArray(new Point[0]));
    }

    @Override
    public Blueprint filter(Blueprint bp) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}


