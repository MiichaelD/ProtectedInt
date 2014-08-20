package ProtectedInt;

/** ProtectedInt is a class used to prevent hacking tools from
 * changing values of integers in runtime.
 * 
 * It uses basic bitwise operations to hold the value as a derived
 * value from 2 random numbers.
 * 
 * NOTE: this class should be SETUP only once */
public class ProtectedInt implements Comparable<Object>{

	private static int leftSecret=-1, rightSecret=-1;
	
	private int left = 0, right = 0;
	
	private static int MAX_RAND_VAL = 32769; //2^16
	
	/** Create a new PRotectedInt with 0 value*/
	public ProtectedInt(){
		left = 0;
		right =0;
		//System.out.println("starting value ="+get());
	}
	
	/** Create a new ProtectedInt with given int value*/
	public ProtectedInt(int v){
		set(v);
	}
	
	/** Create a new ProtectedInt with given String value*/
	public ProtectedInt(String v){
		try{
			set(Integer.parseInt(v));
		}catch(NumberFormatException e){
			left = right = 0;
		}
	}
	
	
	
	/** Setup only once to prevent data loss*/
	public static void setup(){
		leftSecret = (int)(Math.random()*MAX_RAND_VAL);
		rightSecret = (int)(Math.random()*MAX_RAND_VAL);
		//System.out.println("leftSecret ="+leftSecret+", rightSecret ="+rightSecret);
	}
	
	/** Check if ProtectedInt has been setup, this is
	 * helpful to prevent setting up more than once*/
	public static boolean isSetup(){
		return leftSecret >= 0 && rightSecret >= 0;
	}
	
	
	
	/** Set new int value
	 * @return the current value as int*/
	public int set(int v){
		//XOR - OR Exclusive
		left =	v ^ leftSecret;
		right =	v ^ rightSecret;
		return v;
	}
	
	
	/** Get current value as int*/
	public final int get(){
		int leftV =		left ^ leftSecret;
		int rightV =	right ^ rightSecret;
		
		if(leftV != rightV){
			//values corrupted, reset to 0
			left = 0;
			right = 0;
		}
		return leftV;
	}
	
	/** Increment by one the value of ProtectedInt
	 * @return the current value as int*/
	public int increment(){
		return set(get()+1);
	}
	
	/** Decrement by one the value of ProtectedInt
	 * @return the current value as int*/
	public int decrement(){
		return set(get()-1);
	}
	
	/** Add current value and param value and store it
	 * @param v value to add.
	 * @return the current value as int.*/
	public int add(int v){
		return set(get()+v);
	}
	
	/** Subtract and param value to current value and store it
	 * @param v value to subtract.
	 * @return the new value as int.*/
	public int subtract(int v){
		return set(get()-v);
	}
	
	/** Multiply current value by param value and store it
	 * @param v value to multiply.
	 * @return the new value as int.*/
	public int multiply(int v){
		return set(get()*v);
	}
	
	/** Divide current value by param value and store it
	 * @param v divisor.
	 * @return the new value as int.*/
	public int divide(int v){
		return set(get()/v);
	}
	
	/** Get residue from current value divided by param value and store it
	 * @param v divisor.
	 * @return the new value as int, which is the residue of
	 * dividing stored value by param value.*/
	public int module(int v){
		return set(get()%v);
	}

	@Override
	public int compareTo(Object arg0) {
		if( arg0 instanceof Integer)
			return get()-(int)arg0;
		if( arg0 instanceof ProtectedInt)
			return get()-((ProtectedInt)arg0).get();
		throw new ClassCastException("ProtectedInt can only be compared to ints and ProtectedInt type");
	}
}
