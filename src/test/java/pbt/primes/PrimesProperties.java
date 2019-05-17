package pbt.primes;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static pbt.primes.PrimesProperties.Primes.factorize;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.IntRange;

class PrimesProperties {

	@Property
	void jederFactorIstTeiler(@ForAll @IntRange(min = 3, max = 1_000_000) int num) {
		final Collection<Integer> factors = factorize(num);
		assertThat(factors).allMatch(f -> num % f == 0);
	}

	@Property
	void hatFaktoren(@ForAll @IntRange(min = 3, max = 1_000_000) int num) {
		final Collection<Integer> factors = factorize(num);
		assertThat(factors).isNotEmpty();
	}

	@Property
	void jederFaktorIstPrimzahl(@ForAll @IntRange(min = 3, max = 1_000_000) int num) {
		final Collection<Integer> factors = factorize(num);
		assertThat(factors).allMatch(Primes::isPrime);
	}

	@Property
	void faktorenErgebenOriginal(@ForAll @IntRange(min = 3, max = 1_000_000) int num) {
		final Collection<Integer> factors = factorize(num);
		assertThat(num).isEqualTo(factors.stream().reduce(1, (a, b) -> a * b));
	}

	@Property
	void iaeWennWerteKleinerZwei(@ForAll @IntRange(max = 1) int num) {
		try {
			factorize(num);
			fail("Should throw");
		} catch (IllegalArgumentException e) {
		}
	}

	@Property
	void faktorenSindAufsteigend(@ForAll @IntRange(min = 3, max = 1_000_000) int num) {
		final Collection<Integer> factors = factorize(num);
		final List<Integer> sortedFactors = new ArrayList<>(factors);
		Collections.sort(sortedFactors);
		assertThat(factors).containsExactlyElementsOf(sortedFactors);
	}

	@Property
	void primzahlIstEigenerFaktor(@ForAll("primes") int prime) {
		assertThat(factorize(prime)).containsExactly(prime);
	}

	@Provide
	Arbitrary<Integer> primes() {
		return Arbitraries.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83,
				89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193,
				197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313,
				317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443,
				449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587,
				593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719,
				727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859,
				863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009,
				1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109,
				1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223, 1229, 1231, 1237,
				1249, 1259, 1277, 1279, 1283, 1289, 1291, 1297, 1301, 1303, 1307, 1319, 1321, 1327, 1361, 1367, 1373,
				1381, 1399, 1409, 1423, 1427, 1429, 1433, 1439, 1447, 1451, 1453, 1459, 1471, 1481, 1483, 1487, 1489,
				1493, 1499, 1511, 1523, 1531, 1543, 1549, 1553, 1559, 1567, 1571, 1579, 1583, 1597, 1601, 1607, 1609,
				1613, 1619, 1621, 1627, 1637, 1657, 1663, 1667, 1669, 1693, 1697, 1699, 1709, 1721, 1723, 1733, 1741,
				1747, 1753, 1759, 1777, 1783, 1787, 1789, 1801, 1811, 1823, 1831, 1847, 1861, 1867, 1871, 1873, 1877,
				1879, 1889, 1901, 1907, 1913, 1931, 1933, 1949, 1951, 1973, 1979, 1987, 1993, 1997, 1999, 2003, 2011,
				2017, 2027, 2029, 2039, 2053, 2063, 2069, 2081, 2083, 2087, 2089, 2099, 2111, 2113, 2129);
	}

	static class Primes {
		static Collection<Integer> factorize(int num) {
			if (num < 2)
				throw new IllegalArgumentException("num must be bigger than 1. Was: " + num);
			int n = num;
			final Collection<Integer> factors = new ArrayList<>();
			for (int i = 2; i <= n / i; i++) {
				while (n % i == 0) {
					factors.add(i);
					n /= i;
				}
			}
			if (n > 1) {
				factors.add(n);
			}
			return factors;
		}

		static boolean isPrime(int n) {
			if (n == 2)
				return true;
			if (n % 2 == 0)
				return false;
			for (int i = 3; i * i <= n; i += 2) {
				if (n % i == 0)
					return false;
			}
			return true;
		}
	}
}
