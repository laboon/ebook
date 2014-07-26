package com.laboon;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;



public class FizzBuzzTest {

	@SuppressWarnings("unchecked")

	FizzBuzz _fb = null; 
	
	@Before
	public void setUp() throws Exception {
		_fb = new FizzBuzz();
	}
	
	@Test
	public void testNumber() {
		assertEquals(_fb.value(1), "1");
	}
	
	@Test
	public void testNumber2() {
		assertEquals(_fb.value(2), "2");
	}
	
	@Test
	public void testNumber3() {
		assertEquals(_fb.value(3), "Fizz");
	}
	
	@Test
	public void testNumber5() {
		assertEquals(_fb.value(5), "Buzz");
	}
	
	@Test
	public void testNumber15() {
		assertEquals(_fb.value(15), "FizzBuzz");
	}
	
	
}
