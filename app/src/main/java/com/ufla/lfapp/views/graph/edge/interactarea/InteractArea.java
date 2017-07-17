package com.ufla.lfapp.views.graph.edge.interactarea;

import android.graphics.Path;
import android.graphics.PointF;

/**
 * Created by carlos on 11/21/16.
 */

public interface InteractArea extends Cloneable {

    boolean isOnInteractArea(PointF point);

    Path getInteractArea();

    InteractArea clone();

    float distanceToCircumferenceOfSourceVertex(PointF point);

    float distanceFromCircumferences();

    PointF getNearestPoint();

}