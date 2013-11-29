package org.nic.genetics.dameproblem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ChessField
{
    private BooleanProperty freeStatus = new SimpleBooleanProperty();
    
    private BooleanProperty queen = new SimpleBooleanProperty();
    
    private DoubleProperty queenValue = new SimpleDoubleProperty(1.0);

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

    /**
     * @return the queenValue
     */
    public DoubleProperty queenValueProperty()
    {
        return queenValue;
    }
    
    public double getQueenValue()
    {
	return queenValue.get();
    }
    
    public void setQueenValue(final double newValue)
    {
	queenValue.set(newValue);
    }
    
    public void decrementQueenValue()
    {
	queenValue.set(queenValue.get() - 0.2);
    }
    
    public void resetQueenValue()
    {
	queenValue.set(1.0);
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
    
    /**
     * Creates a deep copy of this ChessField object
     * 
     * @return the deep copy of this object
     */
    public ChessField deepCopy()
    {
	ChessField newField = new ChessField();
	
	newField.freeStatus.set(this.freeStatus.get());
	newField.queen.set(this.queen.get());
	newField.queenValue.set(this.getQueenValue());
	
	return newField;
    }
    

}
