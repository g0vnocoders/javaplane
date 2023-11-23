package javaplane;

import javax.swing.SwingUtilities;

import javaplane.Decorators.App;
import javaplane.Presenters.PlanePresenter;
import javaplane.Routers.ResetPlane;
import javaplane.Interactors.FuelControls;
import javaplane.Interactors.FuelControlsStrict;
import javaplane.Objects.Plane;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Plane an140 = new Plane();
            PlanePresenter planePresenter = new PlanePresenter(new App(), new ResetPlane(), new FuelControlsStrict(an140));
            planePresenter.setDebug(true);
        });
    }
}
