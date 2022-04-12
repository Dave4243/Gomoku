import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Name:        David Huang
 * Date:        3/18/2022
 * Course:      AP CSP
 * Description: The Gomoku.java class displays a GUI where players
 * can play the game Gomoku, or 5 in a row, where a player wins
 * when they get 5 (or more) stones in a row.
 */
public class Gomoku extends JFrame implements ActionListener
{
	private static JFrame f;
	private JPanel        p;

	private JButton[][]   buttons;
	private JTextField    winnerText;
	private ImageIcon     black, white;

	private int           turn;

	/**
	 * Constructor
	 */
	public Gomoku()
	{
		f = new JFrame("Gomoku");

		p = new JPanel(new GridBagLayout());

		buttons = new JButton[14][14];
		winnerText = new JTextField();
		winnerText.setEditable(false);
		black = new ImageIcon("black.png");
		white = new ImageIcon("white.png");

		turn = 1;
		GridBagConstraints con = new GridBagConstraints();

		// fills and adds 2d array of JButtons to panel
		for (int r = 0; r < 14; r++)
		{
			for (int c = 0; c < 14; c++)
			{
				buttons[r][c] = new JButton("");
				buttons[r][c].setHorizontalAlignment(JTextField.CENTER);
				Dimension f = new Dimension(45,45);
				buttons[r][c].addActionListener(this);
				buttons[r][c].setPreferredSize(f);
				con.gridx = c;
				con.gridy = r;
				p.add(buttons[r][c], con);
			}
		}
		con.fill  = GridBagConstraints.BOTH;
		con.gridx = 2;
		con.gridy = 14;  
		con.gridwidth = 10; 
		Font gFont = new Font("Neue Helvetica", Font.BOLD, 24);   
		winnerText.setFont(gFont);
		winnerText.setHorizontalAlignment(JTextField.CENTER);
		p.add(winnerText, con);
		f.setContentPane(p);
	}

	/**
	 * Runs Gomoku()
	 */
	public static void main(String a[])
    {
		try
		{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}
		catch (Exception e)
		{
		}

		Gomoku app = new Gomoku();

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setSize(700,740);
		//f.pack();
		f.setVisible(true);
	}

	/**
	 * @param row    row in array of JButton clicked by user
	 * @param column column in array of JButton clicked by user
	 * @return       5 stones of the same color in a row horizontally
	 */
	public boolean horizontal(int row, int column)
	{
		// iterates through columns 0-8; 9-13 don't need to be checked
		for (int i = 0; i < 9; i++)
		{
			// if button has the same color stone as the one just placed...
			if (buttons[row][i].getIcon() == buttons[row][column].getIcon())
			{		
				boolean result = true;

				// checks if 4 stones right of it all have the same color
				for (int x = 1; x < 5; x++)
				{
					if (buttons[row][i+x].getIcon() != buttons[row][column].getIcon())
					{
						result = false;
					}
				}
				// returns true if yes
				if (result == true)
				{
					return true;
				}
			}
		}
		// otherwise, return false
		return false;
	}

	/**
	 * @param row    row in array of JButton clicked by user
	 * @param column column in array of JButton clicked by user
	 * @return       5 stones of the same color in a row vertically
	 */
	public boolean vertical(int row, int column)
	{
		// iterates through rows 0-8; 9-13 don't need to be checked
		for (int i = 0; i < 9; i++)
		{
			// if button has the same color stone as the one just placed...
			if (buttons[i][column].getIcon() == buttons[row][column].getIcon())
			{		
				boolean result = true;

				// checks if the 4 stones below it all have the same color
				for (int x = 1; x < 5; x++)
				{
					if (buttons[i+x][column].getIcon() != buttons[row][column].getIcon())
					{
						result = false;
					}
				}
				// returns true if yes
				if (result == true)
				{
					return true;
				}
			}
		}
		// otherwise, return false
		return false;
	}

	/**
	 * @param row    row in array of JButton clicked by user
	 * @param column column in array of JButton clicked by user
	 * @return       5 stones of same color in a row diagonally descending
	 */
	public boolean diagonal1(int row, int column)
	{
		// number of stones connected to placed stone diagonally above
		int up = 0;

		// number of stones connected to placed stone diagonally below
		int down = 0;

		// checks lower right stone
		for (int i = 1; i < 5; i++)
		{
			// if there is no lower right stone, exit loop
			if (row+i > 13 || column+i > 13)
			{
				break;
			}

			// if there is, then increment down
			if (buttons[row+i][column+i].getIcon() == buttons[row][column].getIcon())
			{
				down++;
			}
		}

		// checks upper left stone
		for(int i =1; i < 5; i++)
		{
			// if there is no upper left stone, exit loop
			if (row-i <0 || column -i < 0)
			{
				break;
			}

			// if there is, then increment up
			if (buttons[row-i][column-i].getIcon() == buttons[row][column].getIcon())
			{
				up++;
			}
		}

		// if more than 4 stones connected (> 5 stones in a row), return true
		if (up + down >= 4)
		{
			return true;
		}
		return false;
	}

	/**
	 * @param row    row in array of JButton clicked by user
	 * @param column column in array of JButton clicked by user
	 * @return       5 stones of same color in a row diagonally ascending
	 */
	public boolean diagonal2(int row, int column)
	{
		// number of stones connected to placed stone diagonally above
		int up = 0;

		// number of stones connected to placed stone diagonally below
		int down = 0;

		// checks upper right stone
		for (int i = 1; i < 5; i++)
		{
			// if there is no upper right stone, exit loop
			if (row -i < 0 || column +i > 13)
			{
				break;
			}

			// if there is, increment up
			if (buttons[row-i][column+1].getIcon() == buttons[row][column].getIcon())
			{
				up++;
			}
		}
		
		// checks lower left stone
		for (int i = 1; i < 5; i++)
		{
			// if there is no lower left stone, exit loop
			if (row +i > 13 || column - i < 0)
			{
				break;
			}

			// if there is, increment down
			if (buttons[row+i][column-i].getIcon() == buttons[row][column].getIcon())
			{
				down++;
			}
		}

		// if more than 4 stones connected (> 5 stones in a row), return true
		if (up+down >=4)
		{
			return true;
		}
		return false;
	}

	/**
	 * Handles user inputs
	 * @param e User input
	 */
	public void actionPerformed(ActionEvent e)
	{
		// rows
		for (int y = 0; y < 14; y++)
		{
			// columns
			for (int z = 0; z < 14; z++)
			{
				if (e.getSource() == buttons[y][z])
				{
					// displays white stone
					if (turn == 0)
					{
						buttons[y][z].setIcon(white);
						turn++;
						buttons[y][z].removeActionListener(this);
					}
					// displays black stone
					else if (turn == 1)
					{
						buttons[y][z].setIcon(black);
						turn--;
						buttons[y][z].removeActionListener(this);
					}
					
					// if 5 in a row ...
					if ((horizontal(y,z) || vertical(y,z)) || (diagonal1(y,z) || diagonal2(y,z)))
					{
						System.out.println("win");
						for (JButton[] arr : buttons)
						{
							for (JButton elem : arr)
							{
								elem.removeActionListener(this);
							}
						}
						if (turn == 1)
						{
							winnerText.setText("White Wins!");
						}
						else
						{
							winnerText.setText("Black Wins!");
						}
					}
				} // end of e.getSource()
			}
		}
	}
}
