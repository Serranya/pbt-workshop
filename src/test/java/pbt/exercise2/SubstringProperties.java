package pbt.exercise2;

import net.jqwik.api.*;
import net.jqwik.api.Tuple.*;

import static org.assertj.core.api.Assertions.*;

class SubstringProperties {

	@Property
	@Label("String.substring() never throws exception")
	void stringSubstringWorks(@ForAll("tuple3") Tuple3<String, Integer, Integer> substringParams) {
		String initialString = substringParams.get1();
		int beginIndex = substringParams.get2();
		int endIndex = substringParams.get3();

		assertThatCode(() -> initialString.substring(beginIndex, endIndex)).doesNotThrowAnyException();
	}

	@Provide
	Arbitrary<Tuple3<String, Integer, Integer>> tuple3() {
		return Arbitraries.strings()
		.all()
		.flatMap(aString ->
			Arbitraries.integers()
			.between(0, aString.length())
			.flatMap(end ->
				Arbitraries.integers()
				.between(0, end)
				.map(begin -> Tuple.of(aString, begin, end))));
	}
}
