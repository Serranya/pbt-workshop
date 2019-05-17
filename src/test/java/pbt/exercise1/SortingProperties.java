package pbt.exercise1;

import java.util.*;

import org.assertj.core.api.*;

import net.jqwik.api.*;

class SortingProperties {

	@Property
	void anyListOfIntegersCanBeSorted(@ForAll List<Integer> aList) {
		Assertions.assertThat(sort(aList)).isNotNull();
	}

	@Property
	<T extends Comparable<T>> void theSizeRemainsTheSame(@ForAll List<T> aList) {
		Assertions.assertThat(aList.size()).isEqualTo(sort(aList).size());
	}

	@Property
	<T extends Comparable<T>> void isIdempotent(@ForAll List<T> aList) {
		List<T> sorted = sort(aList);
		Assertions.assertThat(sorted).containsExactlyElementsOf(sort(sorted));
	}
	
	@Property
	<T extends Comparable<T>> void isVollstaendigeInduktion(@ForAll List<T> aList) {
		isSortiert(sort(aList));
	}
	
	<T extends Comparable<T>> void isSortiert(@ForAll List<T> aList) {
		if(aList.size() < 2) {
			return;
		}
		Assertions.assertThat(aList.get(0)).isLessThanOrEqualTo(aList.get(1));
		isSortiert(aList.subList(1, aList.size()));
	}
	
	

	// Exercise: Write property methods for all properties you have identified

	private <T extends Comparable<? super T>> List<T> sort(List<T> original) {
		List<T> clone = new ArrayList<>(original);
		Collections.sort(clone);
		return clone;
	}

}
