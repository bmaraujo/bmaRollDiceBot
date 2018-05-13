package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import entities.Roll;
import services.RollService;

public class RollServiceTest {
	
	@Test
	public void testRoll() {
		RollService service = new RollService();
		int dices = 1;
		int sides = 3;
		int mod = 0;
		
		Roll roll = new Roll(dices, sides, mod);
		Roll rolled = service.roll(roll);
		
		boolean inRange = (rolled != null && rolled.getRolls().size() > 0 );
		
		if(inRange){
			for(Integer die : rolled.getRolls()) {
				if(die > sides) {
					inRange = false;
				}
			}
		}
		
		assertTrue("All rolls should be smaller or equals to " + sides, inRange);
	}
	
	@Test
	public void testModification() {
		RollService service = new RollService();
		int dices = 1;
		int sides = 3;
		int mod = 4;
		
		Roll roll = new Roll(dices, sides, mod);
		Roll rolled = service.roll(roll);
		
		Integer total =0;
		for(Integer die : rolled.getRolls()) {
			total += die;
		}
		
		assertEquals("Total must include the modification", total + mod, rolled.getTotal());
	}
}
