package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CommercialTest {

	@Test
	public void test_getPrimeAnnuellewithCANull (){
		//GIVEN
		Commercial commercial = new Commercial();
		
		//WHEN
		Double prime = commercial.getPrimeAnnuelle();
		
		//THEN
		Assertions.assertThat(prime).isEqualTo(500d);
	}
}
