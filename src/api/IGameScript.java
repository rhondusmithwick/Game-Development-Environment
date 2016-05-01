package api;

import groovy.lang.GroovyShell;

/**
 * Template for scripts executed within the game loop
 *
 * @author Tom
 */
public interface IGameScript {
    void init (GroovyShell shell, ISystemManager game);

    void update (double dt);
}
