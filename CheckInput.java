/*
    Maria Yampolsky
    Ms. Krasteva
    December 20th, 2018
    AlphaAttack - CheckInput class
    This class is the input and processing portion of the AlphaAttack gameplay.
    It checks what keys the user is hitting and adds the appropriate amount of
    points to the user's score.

    Name            Type            Purpose
    c               Console         This stores the output Console
    a               AlphaAttack     This stores the instance of the main class
    inputChar       char            This stores the user's input that they enter
   
*/
import java.awt.*;
import hsa.Console;

public class CheckInput extends Thread
{
    private Console c;           // The output console
    AlphaAttack a;
    char inputChar;

    /*
    This method is the class constructor, and it is where the instance of the output Console
    and AlphaAttack class we are using are passed.
    */
    public CheckInput (Console con, AlphaAttack alpha)
    {
	c = con;
	a = alpha;
    }


    /*
    This method is where the user's key tapping is collected as input and where it
    is determined whether input should be checked for again, processing should be
    performed or the game should be exitted.

    Name                Type            Purpose
    i                   int             Used as a for loop counter variable.

    If Statements
    1) This if statement checks if the user wants to exit, and if they do, it runs for loop 1 and exits the method
    2) This if statement checks that there are letters on screen before performing processing
    3) This if statement checks if the letter is uppercase, and adds 32 if it is
    4) This if statement checks if the key hit is out of range, and if it is, it recalls the method

    For Loops
    1) This for loop is used when the user wants to exit to reset all the letters to off screen

    */
    private void userInput ()
    {
	a.finished = false;
	inputChar = c.getChar ();
	a.finished = true;
	if (inputChar == 10 && a.letterNum > 0) //if statement 1
	{
	    synchronized (c)
	    {
		for (int i = 0 ; i < 26 ; i++) //for loop 1
		{
		    a.onScreen [i] = false;
		}
		a.continuePlay = false;
		return;
	    }
	}
	if (a.containsTrue (a.onScreen) == true) //if statement 2
	{
	    if (inputChar >= 65 && inputChar <= 90) //if statement 3
		inputChar += 32;
	    if (inputChar < 97 || inputChar > 122) //if statement 4
		userInput ();
	}
    }


    /*
    This method modifies the score if the user hits a valid key for a letter that is
    on screen.

    If Statements
    1) This statement checks that the letter the user entered is on screen at this moment
    2) This statement checks where that letter is, determining how many points to add to the score

    */
    private int addScore (char letter, int yLoc)
    {
	if (a.onScreen [letter - 97] == true) //if statement 1
	{
	    synchronized (c)
	    {
		c.setColor (new Color (168, 209, 247));
		c.fillRect (a.xLocation [letter - 97] - 10, yLoc - 30, 50, 50);
	    }
	    a.onScreen [letter - 97] = false;
	    if (yLoc >= 0 && yLoc <= 160) //if statement 2
	    {
		return (a.score + 50);
	    }
	    else if (yLoc > 160 && yLoc <= 320)
	    {
		return (a.score + 30);
	    }
	    else if (yLoc > 320 && yLoc <= 500)
	    {
		return (a.score + 10);
	    }
	}
	return a.score; //if letter is not on screen, score does not change
    }


    /*
    This method runs the Thread for this class. It is called in the AlphaAttack class.

    While Loops
    1) This loop is run to continuously check for input and process it, and it is exited when the
       user wants to quit.

    If Statements
    1) This if statement checks if the loop should be exited now (before input is taken)
    2) This if statement checks if the loop should be exited now (after input is taken)
    3) This if statement checks if the input is a letter, and if it is, it runs the addScore method
    4) This if statement checks if there are still letters on screen, and if so, it displays the score

    */
    public void run ()
    {
	while (true) //while loop 1
	{
	    if (a.continuePlay == false) //if statement 1
		break;
	    userInput ();
	    if (a.continuePlay == false) //if statement 2
		break;
	    if ((inputChar - 97) >= 0 && (inputChar - 97) < 26 || inputChar >= 65 && inputChar <= 90) //if statement 3
		a.score = addScore (inputChar, a.yLocation [inputChar - 97]);
	    if (a.containsTrue (a.onScreen) == true) //if statement 4
	    {
		synchronized (c)
		{
		    c.setColor (Color.white);
		    c.fillRect (580, 480, 60, 20);
		    c.setColor (Color.red);
		    c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 20));
		    c.drawString ("" + a.score, 600, 498);
		}
	    }
	}
    }
} // CheckInput class


