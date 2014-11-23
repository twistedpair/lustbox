package com.github.twistedpair.math;

import java.math.BigDecimal;
import java.math.BigInteger;

final class Fibonacci {

	private static final BigDecimal TWO = new BigDecimal(2);
	private static final BigDecimal ROOT_FIVE = new BigDecimal(Math.sqrt(5));
	private static final BigDecimal A = BigDecimal.ONE.add(ROOT_FIVE).divide(TWO);
	private static final BigDecimal B = BigDecimal.ONE.subtract(ROOT_FIVE).divide(TWO);
	
	public static BigInteger constant(int n) {
		BigDecimal a = (A.pow(n).subtract(B.pow(n))).divide(ROOT_FIVE);
		return a.toBigInteger();
	}
	
	public static long recursive(long limit) {
		return fib(limit);
	}
	
	private static long fib(long n) {
		if(n >1) {
			return fib(n-1) + fib(n-2);			
		}
		return n; // allows 1->1, 2->1
	}
	
	//Iteration method
    public static BigInteger iterative(long n) {
        BigInteger f1	= BigInteger.ZERO;
        BigInteger f2	= BigInteger.ONE;
        BigInteger f3	= BigInteger.ONE;
        
        for (long i = 0; i < n; i++) {
            f1 = f2; //1
            f2 = f3; //1
            f3 = f1.add(f2);
        }
        return f1;
    }
}