/*
    Maria Yampolsky
    Ms. Krasteva
    December 20th, 2018
    AlphaAttack
    This program allows the user to play the typing game Alpha Attack. The program will start with an animation as an
    introduction, and will then lead to the main menu. The main menu provides the user with four choices. Option 1 is
    to play the game, option 2 is to see the instructions, option 3 is to see the list of high scores, and option 4
    allows the user to exit the game, making the program close. When you select option 1, you are taken to a level
    selection screen, where you can choose 1) easy, 2) medium, 3) difficult or 4) to exit. The higher difficulties have
    letters that drop at higher speeds. After choosing difficulty, the user is then prompted to enter their name for the
    leaderboard. The game then counts down from 3 before beginning. The user can click Enter at any time while playing to
    exit. Twenty letters will fall during the game, and more points are awarded for each letter the higher on the screen
    the user clicks it. For every letter the user misses, 20 points are subtracted from their score. The highest possible
    score is 1000, and the lowest is -400. If the user selects to see the instructions from the main menu, they will see
    instructions for game play and for controlling game flow. If they select to see the high scores, they will be shown a
    screen with the top ten scores in each category of difficulty. They can then hit 1 to enter Administrative Mode, where
    they can enter the password and hit 1 to clear the high scores lists. If they choose to exit from the main menu, they
    will be shown a goodbye screen.


    Name                Type                        Purpose
    c                   Console                     This will store the output Console on which the game will be played
    choice              char                        This variable stores the user's menu choice, deciding the flow of the game
    a                   AlphaAttack                 This will store the instance of this class to pass to other classes
    onScreen            boolean[]                   A boolean array of length 26 that stores whether or not a given letter (the letter's
						    position in the alphabet corresponds to its position in the array) is on screen at a given moment
    xLocation           int[]                       An integer array that stores the x location of any given letter, with the letter's position in the
						    alphabet corresponding with its position in the array
    yLocation           int[]                       An integer array that stores the y location of any given letter, with the letter's position in the
						    alphabet corresponding with its position in the array
    score               int                         This stores the user's score for the current round
    continuePlay        boolean                     This variable stores whether the user should continue playing or not, it controls the flow of the gameplay
    name                String                      This variable stores a user's name. The user is prompted to enter their name when they start playing
    difficulty          int                         This variable stores which difficulty level the user is playing on, 1 for easy, 2 for medium and 3 for hard
    SCORES_FILE         final String                This will store the name of the file that holds the high score list
    letterNum           int                         This variable stores the number of letters on screen at a time
    finished            boolean                     This variable checks whether input has been collected while playing or if the game is still waiting to collect input
*/


// The "AlphaAttack" class.
import java.awt.*;
import javax.swing.JOptionPane;
import hsa.Console;
import java.io.*;

public class AlphaAttack
{
    Console c;           // The output console
    char choice = '0';
    static AlphaAttack a;
    boolean[] onScreen = new boolean [26];
    int[] xLocation = new int [26];
    int[] yLocation = new int [26];
    int score = 0;
    boolean continuePlay = true;
    boolean finished = false;
    String name = "";
    int difficulty = 0;
    final String SCORES_FILE = "highScores.alpha";
    int letterNum = 0;


    /*
    Constructor method
    */
    public AlphaAttack ()
    {
	c = new Console ();
    }


    /*
    This method clears the screen and draws the game title.
    */
    private void drawTitle ()
    {
	c.clear ();
	c.setColor (new Color (168, 209, 247));
	c.fillRect (0, 0, 640, 500);
	c.setColor (Color.black);
	c.setFont (new Font ("Franklin Gothic Demi", Font.PLAIN, 30));
	c.drawString ("AlphaAttack", 245, 30);
    }


    /*
    This method runs the splash screen by starting the Thread that runs the animation.

    Name            Type                Purpose
    s               SplashScreen        This variable stores the instance of the SplashScreen class

    Try-Catch Statements
    1)  This statement checks whether the Thread is Interrupted while running to allow the SplashScreen
	Thread to run without error.

    While
    1) This While Loop collects input if the user is tapping keys to prevent excess input from being collected later.
    */
    public void splashScreen ()
    {
	SplashScreen s = new SplashScreen (c);
	s.start ();
	try //try-catch statement 1
	{
	    s.join ();
	}
	catch (InterruptedException e)
	{
	}

	//isCharAvail() method found in HSA library
	while (c.isCharAvail ()) //while loop 1
	    c.getChar ();
    }


    /*
    This method adds the user's score, name and difficulty level to the high scores file.

    Name        Type            Purpose
    output      PrintWriter     To store the PrintWriter and write the information to the file

    Try-Catch Statements
    1)  This try-catch statement checks for an IOException, which allows us to access files without errors

    */
    private void makeScoreList ()
    {
	PrintWriter output;
	try //try-catch statement 1
	{
	    output = new PrintWriter (new BufferedWriter (new FileWriter (SCORES_FILE, true)));
	    output.println ("level of difficulty:" + difficulty);
	    output.println (name);
	    output.println (score);
	    output.close ();
	}
	catch (IOException e)
	{
	}
    }


    /*
    Creates letters and moves them down screen, will extend Thread to run at the same time as the processing method,
    this is the graphics that go along with the game. Will call the class DropLetter, which extends Thread.

    Name            Type                Purpose
    d               DropLetter          This variable stores the instance of the DropLetter class

    Try-Catch Statements
    1)  This statement checks whether the Thread is Interrupted while running to call a delay between the formation of
	new letters.
    */
    public void dropLetter ()
    {
	DropLetter d = new DropLetter (c, a);
	d.start ();
	try //try-catch statement 1
	{
	    Thread.sleep (550 + (3 - difficulty) * 100);
	}
	catch (InterruptedException e)
	{
	}

    }


    /*
    Processes user input to properly add points to the score and determine whether the user
    wants to exit the game. Will call the class CheckInput, which extends Thread.

    Name            Type                Purpose
    i               CheckInput          This variable stores the instance of the CheckInput class

    */
    public void checkInput ()
    {
	CheckInput i = new CheckInput (c, a);
	i.start ();
    }


    /*
    This method gives the user the option of either reading the instructions, playing the game, seeing
    the high scores list or exiting. It calls drawTitle to clear the screen. It is errortrapped to ensure
    the user enters a valid option.

    Name            Type                Purpose
    i               int                 A loop variable used to draw the scene graphics
    y               int                 A loop variable used to draw the scene graphics

    For Loops
    1)  This for loop allows us to draw windows on the first building, by moving down the rows
    2)  This for loop allows us to draw windows on the first building, by moving left by the columns
    3)  This for loop allows us to draw windows on the second building, by moving down the rows
    4)  This for loop allows us to draw windows on the second building, by moving left by the columns
    5)  This for loop allows us to draw windows on the third building, by moving down the rows
    6)  This for loop allows us to draw windows on the third building, by moving left by the columns
    7)  This for loop allows us to draw windows on the fourth building, by moving down the rows
    8)  This for loop allows us to draw windows on the fourth building, by moving left by the columns

    If Statements
    1)  This if statement checks that the key the user hit is in the correct range of 1 to 4, and if it is not,
	it gives them an error message and asks them to re-enter input.

    While Loops
    1)  This loop asks prompts for the user input and collects it. It stops running when the user enters a valid
	value for their choice.

    */
    public void mainMenu ()
    {
	drawTitle ();

	//graphics
	c.setColor (new Color (136, 137, 140));
	c.fillRect (10, 400, 150, 100);
	c.setColor (new Color (183, 222, 229));
	for (int y = 400 ; y < 500 ; y += 20) //for loop 1
	{
	    for (int i = 10 ; i < 150 ; i += 20) //for loop 2
		c.fillRect (10 + i, 5 + y, 10, 10);
	}

	c.setColor (new Color (79, 80, 84));
	c.fillRect (180, 380, 120, 120);
	c.setColor (new Color (210, 224, 224));
	for (int y = 380 ; y < 500 ; y += 20) //for loop 3
	{
	    for (int i = 180 ; i < 300 ; i += 20) //for loop 4
		c.fillRect (5 + i, 5 + y, 10, 10);
	}

	c.setColor (new Color (159, 165, 168));
	c.fillRect (320, 410, 160, 90);
	c.setColor (new Color (185, 229, 226));
	for (int y = 410 ; y < 480 ; y += 20) //for loop 5
	{
	    for (int i = 320 ; i < 480 ; i += 20) //for loop 6
		c.fillRect (5 + i, 10 + y, 10, 10);
	}

	c.setColor (new Color (76, 93, 102));
	c.fillRect (500, 370, 130, 130);
	c.setColor (new Color (206, 219, 218));
	for (int y = 370 ; y < 490 ; y += 20) //for loop 7
	{
	    for (int i = 500 ; i < 620 ; i += 20) //for loop 8
		c.fillRect (10 + i, 10 + y, 10, 10);
	}

	c.setColor (Color.yellow);
	c.fillOval (530, 20, 80, 80);
	c.setColor (Color.white);
	c.fillOval (520, 70, 50, 50);
	c.fillOval (560, 70, 50, 50);
	c.fillOval (520, 70, 50, 50);

	c.setColor (Color.black);
	c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 30));
	c.drawString ("1 . Play Game", 240, 120);
	c.drawString ("2 . Instructions", 240, 160);
	c.drawString ("3 . High Scores", 240, 200);
	c.drawString ("4 . Exit", 240, 240);
	c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 22));
	c.drawString ("Enter the number that corresponds with your choice: ", 10, 350);
	while (choice == '0') //while loop 1
	{
	    choice = c.getChar ();
	    if (choice < '1' || choice > '4') //if statement 1
	    {
		choice = '0';
		JOptionPane.showMessageDialog (null, "Please enter a number from 1 to 4", "Error", JOptionPane.ERROR_MESSAGE);
	    }
	}
    }


    /*
    This method displays the instructions for how to play the game, and calls pauseProgram to
    allow the user to return to the main menu whenever they choose.
    */
    public void instructions ()
    {
	drawTitle ();
	c.setFont (new Font ("Franklin Gothic Demi", Font.PLAIN, 22));
	c.setColor (Color.black);
	c.drawString ("Instructions", 70, 75);
	c.setTextBackgroundColour (new Color (221, 232, 255));
	c.setCursor (5, 10);
	c.print ("The purpose of this game is to defend your city from the");
	c.print (' ', 7);
	c.setCursor (6, 10);
	c.print ("falling letter attack. Click the letters on your keyboard");
	c.print (' ', 6);
	c.setCursor (7, 10);
	c.print ("as they fall down the screen. This will make the letters");
	c.print (' ', 7);
	c.setCursor (8, 10);
	c.print ("disappear, stopping them from destroying your city. If you");
	c.print (' ', 5);
	c.setCursor (9, 10);
	c.print ("miss a letter, it will explode, damaging your city. You will");
	c.print (' ', 3);
	c.setCursor (10, 10);
	c.print ("lose points for misses and get more points for letters clicked");
	c.print (' ');
	c.setCursor (11, 10);
	c.print ("higher on the screen. There are three levels of difficulty: ");
	c.print (' ', 3);
	c.setCursor (12, 10);
	c.print ("Easy, Medium and Hard, and as difficulty increases, so");
	c.print (' ', 9);
	c.setCursor (13, 10);
	c.print ("does speed. Check the High Scores from the menu to see how you ");
	c.setCursor (14, 10);
	c.print ("compare to other players!");
	c.print (' ', 38);

	//graphics
	c.setColor (new Color (194, 213, 252));
	c.fillRect (0, 329, 640, 57);
	c.setColor (new Color (143, 193, 242));
	c.fillRect (0, 386, 640, 57);
	c.setColor (new Color (92, 173, 232));
	c.fillRect (0, 443, 640, 57);

	c.setCursor (16, 10);
	pauseProgram ();
	choice = '0';
    }


    /*
    This method checks whether an array contains the item true anywhere. It is used in the program
    to check if there are any letters on screen. It is public because it is also used by the CheckInput class.

    Name                Type            Purpose
    i                   int             This variable is used in a for loop to check the different items in the array of letters,
					to determine whether or not any of the array indices hold true.

    For Loops
    1)  This for loop cycles through the array to check if any of the spots in the array are true.

    If Statements
    1)  Checks if that specific array index has a true, and if it does, it returns true for the method.

    */

    public boolean containsTrue (boolean[] arr)
    {
	for (int i = 0 ; i < 26 ; i++) //for loop 1
	{
	    if (arr [i] == true) //if statement 1
		return true;
	}
	return false;
    }


    /*
    Allows the game to be played. Calls both checkInput and dropLetter. Prompts user to choose difficulty level and
    to enter their name. The user can exit the game at any time by hitting Enter.

    Name                Type                Purpose
    difficultyChar
    nameChae
    name

    If Statements
    1) This if statement checks that the user entered an option within the range of 1 to 4
    2) This if statement checks whether the user entered 4, and if they did, it exits the method
    3) This if statement checks for different conditions to allow the user to properly enter a name. It allows them to backspace, add characters
       and hit enter when they have completed typing their name
    4) This if statement checks whether continuePlay is false, and if it is, it exits the loop to end the gameplay
    5) This if statement checks whether there are any letters left on screen, and if there are not, it exits the loop
    6) This if statement checks whether the checkInput class is still waiting to recieve input, and if it is not, it runs pauseProgram. If it is, then it runs while loop #4
    7) This if statement will break from while loop 4 once the checkInput class has collected its input, allowing the user to exit

    Try-Catch Statements
    1) This try-catch block checks that the option the user selected is a number
    2) This try-catch statement makes a delay for the screen where the game counts down from 3 before starting
    3) This try-catch statement makes a delay to keep this main class paused, while the letters continue falling down screen
    4) This try-catch statement is used to make the delay in while loop 4

    While Loops
    1) This loop prompts the user to input whichever level of difficulty the user wants to play, it runs until valid input has been entered.
    2) This while loop collects the user's name to use for the high scores list. It allows the user to enter their name, character by character, and runs until they hit Enter.
    3) This while loop will delay the main program to not finish running until all the letters are off the screen
    4) This while loop will delay the main program to wait until the checkInput class has recieved its last input

    For Loops
    1) This for loop counts down from 3 to 1 before the gameplay begins
    2) This for loop goes trough the onScreen[] and yLocation[] arrays to reset the values before the gameplay begins
    3) This for loop allows for actual game play by calling dropLetter 20 times to make 20 letters fall down the screen

    */
    public void playGame ()
    {
	letterNum = 0;
	difficulty = 0;
	char difficultyChar = 0;
	char nameChar = 0;
	name = "";
	drawTitle ();
	c.setColor (Color.red);
	c.fillRoundRect (220, 115, 200, 80, 10, 10);
	c.setColor (Color.pink);
	c.fillRoundRect (220, 210, 200, 80, 10, 10);
	c.setColor (Color.orange);
	c.fillRoundRect (220, 305, 200, 80, 10, 10);
	c.setColor (Color.white);
	c.drawString ("1. Easy", 270, 160);
	c.drawString ("2. Medium", 250, 255);
	c.drawString ("3. Hard", 270, 350);
	c.setColor (Color.black);
	c.setFont (new Font ("Franklin Gothic Demi", Font.PLAIN, 18));
	c.drawString ("Enter the number that corresponds with your desired level of Game Play ", 5, 85);
	c.drawString ("difficulty, or enter 4 to go back to the menu: ", 5, 107);
	while (difficulty == 0) //while loop 1
	{
	    try //try-catch statement 1
	    {
		difficultyChar = c.getChar ();
		difficulty = difficultyChar - 48;
		if (difficulty < 1 || difficulty > 4) //if statement 1
		{
		    JOptionPane.showMessageDialog (null, "Please enter a number from 1 to 4", "Error", JOptionPane.ERROR_MESSAGE);
		    difficulty = 0;
		}
		if (difficulty == 4) //if statement 2
		{
		    choice = '0';
		    return;
		}
	    }
	    catch (NumberFormatException e)
	    {
		JOptionPane.showMessageDialog (null, "Please enter an integer", "Error", JOptionPane.ERROR_MESSAGE);
		difficulty = 0;
	    }
	}

	c.drawString ("Enter a name for the leaderboard: ", 5, 460);
	c.drawString ("(maximum 10 characters)", 5, 475);
	c.setColor (Color.yellow);
	c.fillRoundRect (300, 425, 100, 60, 10, 10);
	c.setTextBackgroundColor (Color.yellow);

	//this block is inspired by the code Vansh wrote to get input and limit length
	while (nameChar != 10) //while loop 2
	{
	    nameChar = c.getChar ();
	    if (nameChar == 8 && name.length () > 0) //if statement 3
		name = name.substring (0, name.length () - 1);
	    else if ((nameChar == 10 || nameChar == ' ') && name.length () == 0)
		nameChar = 0;
	    else if (name.length () < 10 && nameChar != 10)
		name += nameChar;
	    c.fillRoundRect (300, 425, 100, 60, 10, 10);
	    c.setCursor (23, 39);
	    c.print (name);
	}
	//this block is inspired by the code Vansh wrote to get input and limit length

	continuePlay = true;
	score = 0;
	drawTitle ();
	c.setColor (Color.black);
	c.drawString ("While playing hit Enter at any time to exit.", 32, 250);

	for (int i = 3 ; i > 0 ; i--) //for loop 1
	{
	    c.setColor (Color.black);
	    c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 40));
	    c.drawString ("" + i, 310, 350);
	    try //try-catch statement 2
	    {
		Thread.sleep (1000);
	    }
	    catch (InterruptedException e)
	    {
	    }
	    c.setColor (new Color (168, 209, 247));
	    c.fillRect (310, 320, 20, 31);
	}

	c.fillRect (0, 0, 640, 500);
	c.setColor (Color.white);
	c.fillRect (580, 480, 60, 20);
	c.setColor (Color.red);
	c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 20));
	c.drawString ("" + score, 600, 498);

	//begin playing game
	for (int i = 0 ; i < 26 ; i++) //for loop 2 -> reset
	{
	    onScreen [i] = false;
	    yLocation [i] = -1;
	}
	checkInput (); //input component of game
	for (int i = 0 ; i < 20 ; i++) //for loop 3
	{
	    dropLetter (); //graphics component of game
	    if (continuePlay == false) //if statement 4
	    {
		break;
	    }
	}
	//waits for letters to fall down screen
	while (true) //while loop 3
	{
	    if (containsTrue (onScreen) == false) //if statement 5
	    {
		break;
	    }
	    try //try-catch statement 3
	    {
		Thread.sleep (1);
	    }
	    catch (InterruptedException e)
	    {
	    }
	}
	makeScoreList (); //adds name & score to score file
	drawTitle (); //clears screen
	c.setColor (new Color (244, 215, 66));
	c.fillRect (100, 120, 440, 260);
	c.setColor (Color.black);
	c.drawString ("You completed that round,", 150, 200);
	c.drawString ("and achieved a score of ", 155, 230);
	c.drawString ("" + score, 310, 260);
	choice = '0';
	continuePlay = false;
	c.setCursor (18, 15);
	c.setTextBackgroundColor (new Color (244, 215, 66));
	if (finished == true) //if statement 6
	{
	    pauseProgram ();
	}
	else
	{
	    c.print ("Hit any key to continue: ");
	    while (true) //while loop 4
	    {
		if (finished == true) //if statement 7
		{
		    break;
		}
		try //try-catch statement 4
		{
		    Thread.sleep (1);
		}
		catch (InterruptedException e)
		{
		}
	    }

	}
    }


    /*
    This method displays the top ten high scores for each category, and gives the user the option of entering administrative mode,
    which they can use to clear the list of high scores.

    Name                Type                    Purpose
    msg                 int                     This stores the JOptionPane message result
    adminChoice         char                    This allows to control flow when in administrative mode
    scoresArr           int[][]                 This stores the high scores for the easy, medium and hard levels
    namesArr            String[][]              This stores the names for the score lists for easy, medium and hard
    line                String                  This stores the lines read from the file
    length[]            int[]                   This stores the lengths of the three different scores lists
    place[]             int[]                   This stores the position in the different level arrays in a loop
    x                   int                     This is used as a counting variable in for loops
    i                   int                     This is used as a counting variable in for loops
    y                   int                     This is used as a counting variable in for loops
    temp                int                     This is a variable used when sorting to store values to prevent losing them in the sort
    tempStr             String                  This is used when sorting to prevent losing the name values in the sort

    For Loops
    0) This for loop goes through the three different level arrays
    1) This for loop goes through the scores as part of the sorting algorithm
    2) This for loop goes through the scores as part of the sorting algorithm
    7) This for loop outputs the top ten scores for the easy level
    8) This for loop outputs the top ten scores for the medium level
    9) This for loop outputs the top ten scores for the hard level
    10) This for loop outputs the top ten scores for the easy level to the high scores file
    11) This for loop outputs the top ten scores for the medium level to the high scores file
    12) This for loop outputs the top ten scores for the hard level to the high scores file

    While Loops
    1) This while loop goes through the file and reads until the line is empty, meaning the file has been read fully.
    2) This while loop goes through the file and reads until the line is empty, meaning the file has been read fully.
    3) This while loop prompts the user to enter the Administrative password, and runs until they choose to leave

    Try-Catch Statements
    1) This try-catch allows us to open the high scores file to read it
    2) This try-catch errortraps to ensure that the number we are taking for the score is an integer.
    3) This try-catch errortraps to ensure that the number we are taking for the score is an integer.
    4) This try-catch errortraps to ensure that the number we are taking for the score is an integer.
    5) This try-catch allows us to open the high scores file to write the top ten high scores on to it
    6) This try-catch allows us to open the high scores file and clear it

    If Statements
    1) This if statement checks whether the line read is empty or not, and if it is, it exits the loop. It also checks which level of
       difficulty the score is down for to add one to the appropriate length.
    2) This if statement checks whether the line read is empty or not, and if it is, it exits the loop.
    3) This if statement checks which level of difficulty the score was put for to add it to the correct array.
    4) This if statement is part of the sorting algorithm to check if the value at a particular index of the array needs to be switched with another
    5) This if statement is part of the sorting algorithm to check if the value at a particular index of the array needs to be switched with another
    6) This if statement is part of the sorting algorithm to check if the value at a particular index of the array needs to be switched with another
    7) This if statement checks if the user wants to go to administrative mode or return to main menu
    8) This if statement checks if the user entered the correct password
    9) This if statement checks if the user wants to re-enter the password if they got it wrong, or return to main menu
    10) This if statement checks if the user entered 1 to clear the file, or anything else to return to main menu

    */
    public void displayScores ()
    {
	drawTitle ();
	int msg = 0;
	char adminChoice = 0;
	int[] [] scoresArr = new int [3] [0];
	String[] [] namesArr = new String [3] [0];
	String line = " ";
	int length[] = {0, 0, 0};
	int place[] = {0, 0, 0};
	int temp = 0;
	String tempStr = "";

	PrintWriter output;
	BufferedReader input;
	try //try-catch statement 1
	{
	    input = new BufferedReader (new FileReader (SCORES_FILE));
	    while (line != null) //while loop 1
	    {
		line = input.readLine ();
		if (line == null) //if statement 1
		    break;
		else if (line.equals ("level of difficulty:1"))
		    length [0]++;
		else if (line.equals ("level of difficulty:2"))
		    length [1]++;
		else if (line.equals ("level of difficulty:3"))
		    length [2]++;
	    }
	    input.close ();

	    //set lengths to arrays
	    for (int i = 0 ; i < 3 ; i++)
	    {
		scoresArr [i] = new int [length [i]];
		namesArr [i] = new String [length [i]];
	    }

	    input = new BufferedReader (new FileReader (SCORES_FILE));
	    line = " ";
	    while (line != null) //while loop 2
	    {
		line = input.readLine ();
		if (line == null) //if statement 2
		    break;
		if (line.equals ("level of difficulty:1")) //if statement 3
		{
		    line = input.readLine ();
		    namesArr [0] [place [0]] = line;
		    line = input.readLine ();
		    try //try catch statement 2
		    {
			scoresArr [0] [place [0]] = Integer.parseInt (line);
		    }
		    catch (NumberFormatException e)
		    {
		    }
		    place [0]++;
		}
		else if (line.equals ("level of difficulty:2") && length [1] != 0)
		{
		    namesArr [1] [place [1]] = input.readLine ();
		    try //try-catch statement 3
		    {
			scoresArr [1] [place [1]] = Integer.parseInt (input.readLine ());
		    }
		    catch (NumberFormatException e)
		    {
		    }
		    place [1]++;
		}
		else if (line.equals ("level of difficulty:3") && length [2] != 0)
		{
		    namesArr [2] [place [2]] = input.readLine ();
		    try //try catch statement 4
		    {
			scoresArr [2] [place [2]] = Integer.parseInt (input.readLine ());
		    }
		    catch (NumberFormatException e)
		    {
		    }
		    place [2]++;
		}
	    }

	    input.close ();


	    //sort easy level scores

	    for (int y = 0 ; y < 3 ; y++) //for loop 0
	    {
		for (int x = 0 ; x < length [y] ; x++) //for loop 1
		{
		    for (int i = 0 ; i < length [y] - 1 - x ; i++) //for loop 2 //sorts the array
		    {
			if (scoresArr [y] [i] < scoresArr [y] [i + 1]) //if statement 4
			{
			    temp = scoresArr [y] [i + 1];
			    tempStr = namesArr [y] [i + 1];
			    scoresArr [y] [i + 1] = scoresArr [y] [i];
			    namesArr [y] [i + 1] = namesArr [y] [i];
			    scoresArr [y] [i] = temp;
			    namesArr [y] [i] = tempStr;

			}
		    }
		}
	    }

	}
	catch (IOException e)
	{
	}

	//output high scores
	c.setColor (new Color (244, 182, 66));
	c.setTextBackgroundColor (new Color (244, 182, 66));
	c.fillRoundRect (34, 70, 168, 390, 10, 10);
	for (int i = 0 ; i < Math.min (10, scoresArr [0].length) ; i++) //for loop 7
	{
	    c.setCursor (9 + i, 9);
	    c.print (namesArr [0] [i], 10);
	    c.print (" " + scoresArr [0] [i]);
	}

	c.setColor (new Color (249, 220, 74));
	c.setTextBackgroundColor (new Color (249, 220, 74));
	c.fillRoundRect (236, 70, 168, 390, 10, 10);
	for (int i = 0 ; i < Math.min (10, scoresArr [1].length) ; i++) //for loop 8
	{
	    c.setCursor (9 + i, 34);
	    c.print (namesArr [1] [i], 10);
	    c.print (" " + scoresArr [1] [i]);
	}

	c.setColor (new Color (255, 160, 122));
	c.setTextBackgroundColor (new Color (255, 160, 122));
	c.fillRoundRect (438, 70, 168, 390, 10, 10);
	for (int i = 0 ; i < Math.min (10, scoresArr [2].length) ; i++) //for loop 9
	{
	    c.setCursor (9 + i, 59);
	    c.print (namesArr [2] [i], 10);
	    c.print (" " + scoresArr [2] [i]);
	}

	c.setColor (new Color (4, 0, 40));
	c.drawString ("Easy", 80, 120);
	c.drawString ("Medium", 265, 120);
	c.drawString ("Hard", 484, 120);

	try //try-catch statement 5
	{
	    output = new PrintWriter (new BufferedWriter (new FileWriter (SCORES_FILE)));

	    for (int i = 0 ; i < Math.min (10, scoresArr [0].length) ; i++)    //for loop 10
	    {
		output.println ("level of difficulty:1");
		output.println (namesArr [0] [i]);
		output.println (scoresArr [0] [i]);
	    }

	    for (int i = 0 ; i < Math.min (10, scoresArr [1].length) ; i++)
	    {
		output.println ("level of difficulty:2"); //for loop 11
		output.println (namesArr [1] [i]);
		output.println (scoresArr [1] [i]);
	    }
	    
	    for (int i = 0 ; i < Math.min (10, scoresArr [2].length) ; i++)
	    {
		output.println ("level of difficulty:3"); //for loop 12
		output.println (namesArr [2] [i]);
		output.println (scoresArr [2] [i]);
	    }
	    output.close ();
	}
	catch (IOException e)
	{
	}

	choice = '0';
	c.setCursor (24, 5);
	c.setTextBackgroundColor (new Color (168, 209, 247));
	c.print ("Hit 1 to proceed to Administrative mode, hit any other key to continue: ");
	adminChoice = c.getChar ();
	if (adminChoice == '1') //if statement 7
	{
	    while (true) //while loop 3
	    {
		drawTitle ();
		c.drawString ("Enter the Administrative password to proceed: ", 5, 100);
		c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 16));
		c.drawString ("(line 820 of the program)", 7, 120);
		c.setCursor (8, 5);
		String password = c.readLine ();
		if (password.equals ("AlphaAttackIsFun")) //if statement 8
		{
		    c.setFont (new Font ("Franklin Gothic Demi", Font.PLAIN, 30));
		    c.drawString ("Hit 1 to clear the high score list, and any other", 5, 200);
		    c.drawString ("key ot exit.", 5, 230);
		    adminChoice = c.getChar ();
		    if (adminChoice == '1') //if statement 9
		    {
			try //try-catch statement 6
			{
			    output = new PrintWriter (new BufferedWriter (new FileWriter (SCORES_FILE)));
			    output.close ();
			}
			catch (IOException e)
			{
			}
			c.setCursor (14, 5);
			c.println ("You have succesfully cleared the scores list.");
			c.setCursor (15, 5);
			pauseProgram ();
			break;
		    }
		    else
			break;
		}
		else
		{
		    msg = JOptionPane.showConfirmDialog (null, "You entered the wrong password. Would you like to try again?", "Error", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
		    if (msg == JOptionPane.NO_OPTION) //if statement 10
			break;
		}
	    }
	}
    }


    /*
    This method shows a goodbye message and closes the game window

    Try-catch Statements
    1) This try-catch is used to delay by two seconds before closing the window
    */
    public void goodbye ()
    {
	drawTitle ();
	c.setColor (Color.black);
	c.setFont (new Font ("Franklin Gothic Medium Cond", Font.PLAIN, 30));
	c.drawString ("Thank you for using this program!", 20, 90);
	c.drawString ("By: Maria Yampolsky", 20, 130);
	try
	{
	    Thread.sleep (2000);
	}
	catch (InterruptedException e)
	{
	}
	c.close ();
    }


    /*
    This method is collect input to move on, so it pauses the program and allows input to remain on the screen until the user chooses
    to continue.
    */
    public void pauseProgram ()
    {
	c.print ("Hit any key to continue: ");
	c.getChar ();
    }


    /*
    This method is the main method of this game and allows for everything to run.

    While Loops
    1) This while loop allows the entire program to run until the user enters 4 to exit

    If Statements
    1) This if statement controls program flow by calling the appropriate method depending on what the user enters.

    */
    public static void main (String[] args)
    {
	a = new AlphaAttack ();
	a.splashScreen ();
	while (a.choice != '4') //while loop 1
	{
	    a.mainMenu ();
	    if (a.choice == '1') //if statement 1
		a.playGame ();
	    else if (a.choice == '2')
		a.instructions ();
	    else if (a.choice == '3')
		a.displayScores ();
	}
	a.goodbye ();

    } // main method
} // AlphaAttack class
