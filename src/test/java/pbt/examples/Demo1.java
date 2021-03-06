package pbt.examples;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.*;

class Demo1 {

	@Property
	void lengthOfStringAlwaysPositive(@ForAll String aString) {
		assertThat(aString.length()).isGreaterThanOrEqualTo(0);
	}

	@Property
	boolean absoluteValueOfIntegerAlwaysPositive(@ForAll int anInteger) {
		return Math.abs(anInteger) >= 0;
	}

	@Property
	boolean sumOfTwoIntegersAlwaysGreaterThanEach(
			@ForAll int positive1, //
			@ForAll int positive2
	) {
		int sum = positive1 + positive2;
		return sum > positive1 && sum > positive2;
	}

}
