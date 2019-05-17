package pbt.exercise2;

import org.assertj.core.api.*;

import net.jqwik.api.*;

import static org.assertj.core.api.Assertions.*;

class AddressProperties {

	@Property
	@Label("Address instances are valid")
	void addressInstancesAreValid(@ForAll("address") Address anAddress) {
		assertThat(anAddress).is(anyOf(
				instanceOf(StreetAddress.class),
				instanceOf(PostOfficeBox.class)
		));
		assertThat(anAddress.city()).isNotEmpty();
		if (anAddress.zipCode().isPresent()) {
			isValidGermanZipCode(anAddress.zipCode().get());
		}
	}

	private void isValidGermanZipCode(@ForAll String germanZipcode) {
		assertThat(germanZipcode.chars()).allMatch(c -> c >= '0' && c <= '9');
		assertThat(germanZipcode).doesNotStartWith("00");
	}

	private Condition<? super Address> instanceOf(final Class<?> expectedType) {
		return new Condition<Address>() {
			@Override
			public boolean matches(Address value) {
				return value.getClass() == expectedType;
			}
		};
	}

	Arbitrary<Address> address() {
		Arbitrary<Country> countries = Arbitraries.of(Country.class);
		Arbitrary<Boolean> addressType = Arbitraries.of(true, false);
		Arbitrary<String> cities = Arbitraries.strings().ofMinLength(1);
		Arbitrary<String> identifiers = Arbitraries.strings().ofMinLength(1);
		Arbitrary<String> streets = Arbitraries.strings().ofMinLength(1).ofMaxLength(30);
		Arbitrary<String> houseNumbers = Arbitraries.strings().ofMinLength(1);

		return Combinators.combine(countries, zipCodes(), addressType, cities, identifiers, streets)
				.as((country, zipCode, type, city, id, street) -> {
			String actualZipCode = country == Country.GERMANY ? zipCode : null;
			if (type) {
				new PostOfficeBox(country, city, actualZipCode, id);
			}
			return new StreetAddress(country, city, actualZipCode, street, null);
		});
	}

	Arbitrary<String> zipCodes() {
		return Arbitraries.strings()
				.numeric()
				.ofLength(5)
				.filter(s -> !s.startsWith("00"));
	}
}
