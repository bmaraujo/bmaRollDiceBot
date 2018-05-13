/**
 * 
 */
package services;


import entities.*;


/**
 * @author v-araujb-ave
 *
 */
public class RollService
{

	private static RollService instance;
	/**
	 * Singleton
	 */
	public static RollService getInstance()
	{
		if(instance == null) {
			instance = new RollService();
		}
		return instance;
	}
	
	public Roll roll(Roll roll) {
		for(int i=0;i<roll.getDices();i++) {
			Double number = Double.valueOf(Math.rint(Math.random()*roll.getSides()));
			//rint can round to 0
			if(number == 0d) {
				number = 1d;
			}
			roll.addRoll( (int) (number/1) ); //Remove decimals
		}
		return roll;
	}
}
