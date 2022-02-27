package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Dice implements Serializable{
	private static final long serialVersionUID = 2607984121748460733L;

	private static int count = 0, rollCount = 0;
	
	private int value, sides, id, total;
	
	private Random random;
	
	public Dice(int sides, int total) {
		this.sides = sides;
		this.total = total;
		
		id = ++count;
		random = new Random();
		value = random.nextInt(sides)+1;
	}
	
	public void roll() {
		int previous = value;
		value = random.nextInt(sides)+1;
		++rollCount;
		
		String text = rollCount+"-) Dice_"+(id%total==0 ? total :id%total) 
				+"["+previous+"->"+value+"]\t";
		
		System.out.print(text);
		
		if(rollCount%5==0)
			System.out.println();
	}

	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return super.toString()+" [id="+id+", value="+value+", sides="+sides+"]";
    }
	
	public static void main (String ...strings ){
		ArrayList<Dice> dices = new ArrayList<Dice>();
		
		for(int i=0; i<5; i++) {
			dices.add(new Dice(6 ,5));
		}
		
		for(int i=0;i<1000;i++) {
			dices.forEach(x->{
				x.roll();
			});
		}
	}
}
