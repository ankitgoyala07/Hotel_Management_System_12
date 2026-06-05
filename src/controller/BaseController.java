package controller;

import javax.swing.JFrame;

/**
 * Abstract base class for all controller components that manage a JFrame view.
 * Uses abstraction to isolate common framework lifecycle behaviors.
 *
 * @param <V> the JFrame subclass managed by this controller
 */
public abstract class BaseController<V extends JFrame> {
    protected final V view;

    /**
     * Initializes the controller with its associated JFrame view.
     *
     * @param view the JFrame view instance
     */
    protected BaseController(V view) {
        this.view = view;
    }

    /**
     * Implementations define setup steps such as event listener registration.
     */
    protected abstract void initController();

    /**
     * Renders the managed view visible on screen.
     */
    public void showView() {
        if (view != null) {
            view.setVisible(true);
        }
    }

    /**
     * Disposes of and closes the managed view.
     */
    public void closeView() {
        if (view != null) {
            view.dispose();
        }
    }
}
