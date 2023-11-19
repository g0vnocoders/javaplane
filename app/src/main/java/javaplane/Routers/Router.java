package javaplane.Routers;

import javaplane.Decorators.App;
import javaplane.Objects.Plane;

public interface Router {
    public void reset(Plane plane);
    public void resetView(App app);
}