package org.nic.genetics.dameproblem;

import java.math.BigDecimal;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ChessField
{
    private BooleanProperty freeStatus = new SimpleBooleanProperty(true);

    private BooleanProperty queen = new SimpleBooleanProperty(false);

    private BigDecimal queenValue = new BigDecimal("1.0"); 
    
//    private FloatProperty queenValue = new SimpleFloatProperty(1f);
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
    public BigDecimal queenValueProperty()
    {
	return queenValue;
    }

    public BigDecimal getQueenValue()
    {
	return queenValue;
    }

    public void setQueenValue(BigDecimal newValue)
    {
	queenValue = newValue;
    }

    public void decrementQueenValue()
    {	
	queenValue = queenValue.subtract(new BigDecimal("0.2"));
    }

    public void resetQueenValue()
    {
	queenValue = new BigDecimal("1.0");
    }

    public ChessField()
    {
	freeStatus.set(true);
//	queenValue.setScale(0, RoundingMode.HALF_UP);
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
     * @return the deep copy of this object
     */
    public ChessField deepCopy()
    {
	ChessField newField = new ChessField();

	newField.freeStatus.set(this.freeStatus.get());
	newField.queen.set(this.queen.get());
	newField.queenValue = new BigDecimal(this.getQueenValue().toString());

	return newField;
    }


}
