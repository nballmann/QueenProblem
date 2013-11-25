package org.nic.genetics.dameproblem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.nic.genetics.dameproblem.ChessBoard;
import org.nic.genetics.dameproblem.ChessField;

public class ChessController
{
    @FXML
    private GridPane chessGrid;   

    public ChessBoard chessBoard;

    public ObservableMap<ChessField, AnchorPane> fieldMap = FXCollections.observableHashMap();

    public ChessController() { }

    public void init(final int fieldCount)
    {
	chessBoard = new ChessBoard(fieldCount);

	for (int i = 0; i < chessBoard.getChessFields().size(); i++)
	{
	    for ( int j = 0; j < chessBoard.getChessFields().size(); j++)
	    {
		AnchorPane pane = new AnchorPane();

		if(i%2==0)
		{
		    if(j%2!=0)
		    {
			pane.setStyle("-fx-background-color: black;");
		    }
		    else
		    {
			pane.setStyle("-fx-background-color: white;");
		    }
		}
		else
		{
		    if(j%2!=0)
		    {
			pane.setStyle("-fx-background-color: white;");
		    }
		    else
		    {
			pane.setStyle("-fx-background-color: black;");
		    }
		}

		pane.visibleProperty().bindBidirectional(chessBoard.getChessFields().get(i).get(j).getFreeStatus());

		chessGrid.add(pane, j, i);

		fieldMap.put(chessBoard.getChessFields().get(i).get(j), pane); 
	    }
	}
    }

    public void startSolve(int startRow, int startColumn)
    {
	int count = 0;
	boolean t = true;

	while (count < 1 && t)
	{
	    for (int y = startRow; y < 6; y++)
	    {
		for (int x = startColumn; x < 6; x++)
		{
		    if(chessBoard.getChessFields().get(y).get(x).isFree())
		    {
			chessBoard.getChessFields().get(y).get(x).setQueen(true);

			changeFieldStatus(y, x);

			count++;
		    }
		   break;
		}
	    }
	    t = false;
	}

	System.out.println(count);
    }

    /**
     * Changes the status of fields in a horizontal/vertical cross shape and a diagonal x-shape to false<br>
     * @param rowPos the center row position
     * @param columnPos the center column position
     */
    public void changeFieldStatus(int columnPos, int rowPos)
    {
	for (ObservableList<ChessField> list : chessBoard.getChessFields())
	{
	    list.get(rowPos).changeFreeStatus(false); // all vertical fields

	    int rowPlus = rowPos, rowMinus = rowPos;

	    for(int i = columnPos + 1; i < 6; i++)
	    {
		if(++rowPlus < 6)
		{
		    chessBoard.getChessFields().get(i).get(rowPlus).changeFreeStatus(false);
		}

		if(--rowMinus >= 0)
		{
		    chessBoard.getChessFields().get(i).get(rowMinus).changeFreeStatus(false);
		}
	    }

	    rowPlus = rowPos;
	    rowMinus = rowPos;

	    for(int j = columnPos - 1; j >= 0;j--)
	    {
		if(++rowPlus < 6)
		{
		    chessBoard.getChessFields().get(j).get(rowPlus).changeFreeStatus(false);
		}

		if(--rowMinus >= 0)
		{
		    chessBoard.getChessFields().get(j).get(rowMinus).changeFreeStatus(false);
		}
		
		System.out.println("column: " + j + "rows: " + rowPlus + ":" + rowMinus);
	    }
	}

	for (ChessField f : chessBoard.getChessFields().get(columnPos))
	{
	    f.changeFreeStatus(false); // all horizontal fields
	}
    }

}
