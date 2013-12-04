package org.nic.genetics.dameproblem.controller;

import java.math.BigDecimal;
import java.util.Random;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import org.nic.genetics.dameproblem.ChessBoard;

public class ChessController
{
    public static final int VARIANZ = 11;
    
    public static int ROW_LENGTH;
    
    public static int REBASE;

    @FXML
    private GridPane chessGrid;   

    public ChessBoard chessBoard;

    private Random random = new Random();

    private ObservableList<ChessBoard> resultPool = FXCollections.observableArrayList();
    private ObservableList<ChessBoard> completeResultPool = FXCollections.observableArrayList();

    public ObservableList<AnchorPane> paneList = FXCollections.observableArrayList();

    private long iterations = 0;

    public ChessController() { }

    public void init(final int fieldCount, AnchorPane parent)
    {
	ROW_LENGTH = fieldCount;
	
	REBASE = fieldCount-1;
	
//	GridPane chessGrid2 = new GridPane();
	
	chessBoard = new ChessBoard(fieldCount);

	chessBoard.resetBoardValue();

	generateInitialResultSet();

	if(!chessGrid.getChildren().isEmpty())
	{
	    chessGrid.getChildren().clear();
	}

	if(!paneList.isEmpty())
	{
	    paneList.clear();
	}

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
		//		pane.visibleProperty().bindBidirectional(chessBoard.getChessFields().get(i).get(j).getFreeStatus());

		chessGrid.add(pane, j, i);

		paneList.add(pane); 
		
	    }
	}
//	parent.getChildren().add(chessGrid);
    }

    private BigDecimal boardStatus()
    {
	BigDecimal actualBest = BigDecimal.ZERO;

	for(ChessBoard b : resultPool)
	{
	    if(b.getBoardValue().compareTo(actualBest) > 0)
	    {
		actualBest = b.getBoardValue();
		chessBoard = b;
		//		System.out.println(b.getBoardValue());
	    }
	}

	return actualBest;
    }

    /**
     * starts the genetic solving process:<br>
     * 1. generate initial result set<br>
     * 2. mutate 3 random results<br>
     * 3. generate 3 children out of 3 random pairs with recombined parameters<br>
     * 4. reduce result pool to its original size of 6 (through selection)
     */
    public void startSolve()
    {
	BigDecimal toReach = new BigDecimal(ROW_LENGTH);

	while(boardStatus().compareTo(toReach) != 0)
	{
	    // mutate
	    mutate();

	    // recombine
	    recombine();

	    //reset boardValues
	    for(ChessBoard board : completeResultPool)
	    {
		board.resetBoardValue();
	    }
	    for(ChessBoard board : resultPool)
	    {
		board.resetBoardValue();
	    }

	    // select
	    applySelection();

	    System.out.println(boardStatus());

	    iterations++;
	    
	    Platform.runLater(new Runnable() {

		@Override
		public void run()
		{
		    if(iterations%100 == 0)
		    {
			redrawBoard();
			drawQueens();
		    }
		}
		
	    });
	}

	System.out.println("FINAL: " + boardStatus() + "\nIterations: " + iterations);
	redrawBoard();
	showPerfectResult();
    }

    /**
     * Initial results based of first random ChessBoard<br>
     * fills the resultPool list with 6 identical results
     */
    private void generateInitialResultSet()
    {
	for(int i = 0; i < 6; i++)
	    resultPool.add(chessBoard.deepCopy());
    }

    /**
     * Take 3 results from pool,<br>
     * copy them<br>
     * and randomly mutate the queen positions 
     */
    private void mutate()
    {
	int[] mutators = threeOutOfSix();

	if(!completeResultPool.isEmpty())
	{
	    completeResultPool.clear();
	}

	for(int i = 0; i < 3; i++)
	{
	    completeResultPool.add(resultPool.get(mutators[i]).deepCopy());
	    completeResultPool.get(i).mutateBoard();
	}
    }

    /**
     * generate 3 random result pairs<br>
     */
    private void recombine()
    {
	int[][] parents = generateRandomPairs();

	for(int i = 0; i < 3; i++)
	{
	    completeResultPool.add(resultPool.get(parents[i][0]).generateChild(resultPool.get(parents[i][1])));
	}
    }

    /**
     * generate 6 pairs out of resultPool and discard the worst<br>
     * -> pool back to 6 results of average better quality
     */
    private void applySelection()
    {
	// add resultPool to completeResultPool
	completeResultPool.addAll(resultPool);

	// clear resultPool
	resultPool.clear();

	// 6x :
	int x;
	for(int i = 0; i < 6; i++)
	{
	    // get random pair from completeResultPool
	    ChessBoard a, b;
	    x = random.nextInt(completeResultPool.size());

	    a = completeResultPool.get(x);
	    completeResultPool.remove(x);

	    x = random.nextInt(completeResultPool.size());

	    b = completeResultPool.get(x);
	    completeResultPool.remove(x);

	    // discard the one with worst value or first if equal
	    // add selected result to resultPool
	    if(a.getBoardValue().compareTo(b.getBoardValue()) > 0)
	    {
		resultPool.add(a);
	    }
	    else
	    {
		resultPool.add(b);
	    }
	}
	// after: resultPool.size == 6 completeResultPool.size == 0
    }

    /**
     * Returns three unique random numbers between 0 and 5 (included)
     * @return
     */
    private int[] threeOutOfSix()
    {
	int a = random.nextInt(6), b = random.nextInt(6), c = random.nextInt(6);

	if(a!=b && a!=c && b!=c)
	{
	    return new int[] {a,b,c};
	}
	else
	{
	    threeOutOfSix();
	}

	return new int[] {0,1,2};
    }

    /** 
     * returns three pairs out of the 6 unmodified results
     * @return 
     */
    private int[][] generateRandomPairs()
    {
	int[] lhs = threeOutOfSix();
	int[] rhs = new int[] {-1,-1,-1};

	for(int i = 0; i < 3; i++)
	{
	    int r = random.nextInt(6);

	    for (int j = 0; j < 3; j++)
	    {
		if(lhs[j] == r || rhs[j] == r)
		{
		    i--;
		    break;
		} 
		else if (j==2)
		    rhs[i] = r;
	    }
	}

	return new int[][] {{lhs[0],rhs[0]},{lhs[1],rhs[1]},{lhs[2],rhs[2]}};
    }

    private void showPerfectResult()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(chessBoard.getChessFields().get(i).get(j).isQueen())
		{
		    System.out.print("X ");
		    chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: yellow;");
		}
		else
		{
		    System.out.print("O ");
		}
	    }
	    System.out.println();
	}
    }
    
    private void drawQueens()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(chessBoard.getChessFields().get(i).get(j).isQueen())
		{
		    chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: yellow;");
		}
	    }
	}
    }

    private void redrawBoard()
    {
	for(int i = 0; i < ROW_LENGTH; i++)
	{
	    for (int j = 0; j < ROW_LENGTH; j++)
	    {
		if(i%2==0)
		{
		    if(j%2!=0)
		    {
			 chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: black;");
		    }
		    else
		    {
			 chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: white;");
		    }
		}
		else
		{
		    if(j%2!=0)
		    {
			 chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: white;");
		    }
		    else
		    {
			 chessGrid.getChildren().get((i*ROW_LENGTH)+j).setStyle("-fx-background-color: black;");
		    }
		}
	    }
	}
    }


}
