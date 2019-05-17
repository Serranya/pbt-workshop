package pbt.exercise2;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.*;

class ZipCodeProperties {

	@Property
	@Label("German zip code is valid")
	void germanZipCodeIsValid(@ForAll("zipCodes") String germanZipCode) {
		assertThat(germanZipCode).hasSize(5);
		isValidGermanZipCode(germanZipCode);
	}

	private void isValidGermanZipCode(@ForAll("zipCodes") String germanZipcode) {
		assertThat(germanZipcode.chars()).allMatch(c -> c >= '0' && c <= '9');
		assertThat(germanZipcode).doesNotStartWith("00");
	}

	@Provide
	Arbitrary<String> zipCodes() {
		return Arbitraries.strings()
				.numeric()
				.ofLength(5)
				.filter(s -> !s.startsWith("00"));
	}
}
