/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javatournament.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Xeheros
 */
public class MenuEnLigne extends BasicGameState
{
    private int id;
    
    public MenuEnLigne(int id)
    {
        this.id = id;
    }

    @Override
    public int getID()
    {
        return this.id;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
