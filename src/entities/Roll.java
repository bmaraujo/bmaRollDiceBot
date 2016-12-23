package entities;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 */

/**
 * @author v-araujb-ave
 *
 */
public class Roll
{

	private int dices;
	private int sides;
	private int mod;
	private List<Integer> rolls;
	private int total;
	
	
	/**
	 * 
	 */
	public Roll(int dices, int sides, int mod)
	{
		this.dices = dices;
		this.sides = sides;
		this.mod = mod;
		
		rolls = new ArrayList<Integer>();
		
		total = 0;
	}
	
	public void addRoll(Integer number) {
		rolls.add( number );
		total+=number;
	}
	
	public List<Integer> getRolls(){
		return rolls;
	}
	
	public int getTotal(){
		return total+mod;
	}

	
	public int getDices()
	{
		return dices;
	}

	
	public void setDices(int dices)
	{
		this.dices = dices;
	}

	
	public int getSides()
	{
		return sides;
	}

	
	public void setSides(int sides)
	{
		this.sides = sides;
	}

	
	public int getMod()
	{
		return mod;
	}

	
	public void setMod(int mod)
	{
		this.mod = mod;
	}
}
