
import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

/**
 * Class CryptNum is a small suite of mathematical functions useful
 * for number theory and the study of cryptographic functions. It was 
 * mostly written to solve homework problems in RIT's CSCI 462 course,
 * Introduction to Cryptography, taught by Professor Kaminsky. It has been added
 * to for the purposes of MATH 371, Number Theory, taught by Professor
 * Barth-Hart
 *
 * Author: Jessica Sorrell
 **/
public class CryptNum{


    //Default constructor
    public CryptNum()
    {
    }

    /**
     * Returns a list of all of the primes in the range start and end
     *
     * @param  start  starting point of interval
     * @param end  ending point of interval
     *
     * @return  all irreducible integers in the range [start, end]
     **/
    public int[] generatePrimes( int start, int end ){

	
	int[] primes = new int[135];
	int index = 0;

	Boolean isPrime;
	for (int i = start; i<=end; i+=1){
	    isPrime = true;
	    
	    for (int j = 2; j<= Math.sqrt(i); j++){
		
		if (i % j == 0){
		    isPrime = false;
		    break;
		}
	    }
	    
	    if (isPrime == true){
		primes[index] = i;
		index++;
	    }
	}
	return primes;
    }


    /***
     * Finds the inverse of an integer given a modulus
     ***/
    public int euclidian( int element, int modulus )
	throws InverseException{
	
	if (!this.hasInverse(element, modulus))
	    throw new InverseException(element + "has no inverse mod "
				       + modulus);
	
	int x = modulus;
	int y = element;
	int r = x % y;
	int t2 = 0;
	int t1 = 1;
	int q = 0;
	int t = 0;
	
	while (r!=0){
	    
	    q = (x - r)/y;
	    t = t2 - t1*q;
	    x = y;
	    y = r;
	    t2 = t1;
	    t1 = t;

	    r = x % y;

	}
	if (t1 < 0) t1 += modulus;

	return t1;
    }


    
    /***
     * Finds the inverse of an integer given a modulus
     *
     * @param 
     ***/
    public BigInteger euclidian( BigInteger element, BigInteger modulus )
    throws InverseException{

	    if (!this.hasInverse(element, modulus))
	       	throw new InverseException(element.toString() + 
					   "has no inverse mod "
					   + modulus.toString());
	
	BigInteger x = modulus;
	BigInteger y = element;
	BigInteger r = x.mod( y );
	BigInteger t2 = BigInteger.ZERO;
	BigInteger t1 = BigInteger.ONE;
	BigInteger q = BigInteger.ZERO;
	BigInteger t = BigInteger.ZERO;

    //Run the extended euclidian algorithm
	while (!r.equals(BigInteger.ZERO)){
	    
	    q = (x.subtract(r)).divide(y);
	    t = t2.subtract(t1.multiply(q));
	    x = y;
	    y = r;
	    t2 = t1;
	    t1 = t;
	    
	    r = x.mod(y);

	}
    //Result should be a positive integer over modulus
	if (t1.compareTo(BigInteger.ZERO) == -1){
	    t1 = t1.add(modulus);
	}
	return t1;
    }


    /**
     * Finds the GCD of two integers
     *
     * @param  n  an integer
     * @param  m  the other integer
     *
     * @return  the greatest common divisor of n and m
     **/
    public int GCD( int n, int m){
	if ( m == 0 ) return n;
	return GCD(m, n % m);
    }

    /**
     * Finds the GCD of two BigIntegers
     * 
     * @param  n  a BigInteger
     * @param  m  another BigInteger
     *
     * @return the greatest common divisor of n and m
     **/
    public BigInteger GCD( BigInteger num1, BigInteger num2 ){
	if (num2.equals(BigInteger.ZERO)) return num1;
	return GCD(num2, num1.mod(num2));
    }



    /**
     * Does a given int have an inverse for a given modulus?
     *
     * @param  element  the questionably invertible element
     * @param  modulus  is element invertible mod modulus?
     *
     * @return  true if invertible, false otherwise
     **/
    public Boolean hasInverse( int element, int modulus ){ 
	
	if ( GCD( element, modulus ) == 1 ) return true;
	return false;
    }



    /**
     * Does a given int have an inverse for a given modulus?
     *
     * @param  element  the questionably invertible element
     * @param  modulus  is element invertible mod modulus?
     *
     * @return  true if invertible, false otherwise
     **/
    public Boolean hasInverse( BigInteger element, BigInteger modulus )
    {
	if ( GCD( element, modulus ).equals( BigInteger.ONE )) 
	    return true;
	
	return false;
    }


    /**
     * Given a BigInteger composite, returns a list of all of the 
     * prime factors of composite, with repetitions
     *
     * @param  n  some number to be factored
     *
     * @return  all the prime factors of composite
     **/
    public List<BigInteger> factor( BigInteger n, 
				    List<BigInteger> factors){

	// if n = 1, we're done
	if ( n.equals(BigInteger.ONE) ){
	    return factors;
	}    

	for( BigInteger i = new BigInteger("2"); i.compareTo(n)<=0; 
	     i = i.add(BigInteger.ONE)){
	    
	    if (n.mod(i).equals(BigInteger.ZERO)){
		BigInteger arg = n.divide(i);
		(factor( arg, factors )).add(i);
		
		break;
	    }
	}
    	return factors;
    }
    
    /***
     * eulerPhi takes a BigInteger num and calculates Euler's totient 
     * function for that number
     *
     * @param  num
     *
     * @return  The number of integers relatively prime to num
     ***/
      public BigInteger eulerPhi (BigInteger num){

	List<BigInteger> pfactors = new ArrayList<BigInteger>();
	pfactors = factor( num, pfactors);

	int count = 0;
	BigInteger phi = BigInteger.ONE;
	
	for (int i = 0; i < pfactors.size(); i+= count){
	    
	    count = pfactors.lastIndexOf(pfactors.get(i)) - 
		pfactors.indexOf(pfactors.get(i)) + 1;
	    
	    if (count == 1){
		phi = phi.multiply((pfactors.get(i)).subtract(BigInteger.ONE));
	    }
	    else{
		phi = phi.multiply(((pfactors.get(i)).pow(count)).
				   subtract((pfactors.get(i)).pow(count - 1)));
	    }
	}
	
	return phi;
    }



    /****
     * fermatTest tests a BigInteger num for "industrial-grade" primality. 
     *
     * @param num
     *
     * @return  true if num is an industrial-grade prime, false otherwise
     ***/
    public Boolean fermatTest( BigInteger num ){
	
	Boolean isPrime = true;

	for (BigInteger i = new BigInteger("2"); 
	     i.compareTo(BigInteger.TEN) < 0; i = i.add(BigInteger.ONE)){
	    
	    if(!(i.modPow((num.subtract(BigInteger.ONE)), num))
	       .equals(BigInteger.ONE)){
		isPrime = false;
	    }
	}

	return isPrime;
    }
	

    /*
    public static void main (String args[]){
		
	CryptNum test = new CryptNum();
	int p = 0;
	int q = 0;
	int el = 9;
	int[] primearray = test.generatePrimes( 1001, 2000 );
	
	List<BigInteger> factors = new ArrayList<BigInteger>();
	BigInteger manyFactors = new BigInteger("25401600");	

	factors = test.factor( manyFactors, factors);
	System.out.println( factors );
	
	BigInteger phi = test.eulerPhi( manyFactors );
	System.out.println( "Phi = " + phi);
	
    }
    */
}