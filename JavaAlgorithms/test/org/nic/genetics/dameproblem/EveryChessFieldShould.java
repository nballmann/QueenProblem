package org.nic.genetics.dameproblem;

import org.junit.Test;
import static org.junit.Assert.*;

public class EveryChessFieldShould
{
    @Test
    public void KnowIfItsFree()
    {
	ChessField cf = new ChessField();
	
	assertTrue(cf.isFree());
    }
    
    @Test
    public void BeAbleToHaveAQueenOnIt()
    {
	ChessField cf = new ChessField();
	
	cf.setQueen(true);
	
	assertTrue(cf.isQueen());
    }
}
