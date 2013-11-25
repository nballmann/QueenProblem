package org.nic.genetics.dameproblem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ChessField
{
    private BooleanProperty freeStatus = new SimpleBooleanProperty();
    
    private BooleanProperty queen = new SimpleBooleanProperty();

    /**
     * @return the freeStatusProperty
     */
    public BooleanProperty getFreeStatus()
    {
        return freeStatus;
    }

    /**
     * @return the queenProperty
     */
    public BooleanProperty getQueen()
    {
        return queen;
    }

    public ChessField()
    {
	freeStatus.set(true);
    }
    
    public boolean isFree()
    {
	return freeStatus.get();
    }
    
    public void changeFreeStatus(final boolean newStatus)
    {
	freeStatus.set(newStatus);
    }
    
    public boolean isQueen()
    {
	return queen.get();
    }
    
    public void setQueen(final boolean queenStatus)
    {
	queen.set(queenStatus);
	
	if(queenStatus)
	    freeStatus.set(false);
    }

}
