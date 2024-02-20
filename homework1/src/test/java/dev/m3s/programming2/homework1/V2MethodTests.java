package dev.m3s.programming2.homework1;

import static dev.m3s.programming2.homework1.H1.*;
import static dev.m3s.programming2.homework1.H1Matcher.*;

import java.util.ArrayList;
import java.util.Iterator;

import io.github.staffan325.automated_grading.Points;
import io.github.staffan325.automated_grading.TestCategories;
import io.github.staffan325.automated_grading.TestCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

@EnabledIfEnvironmentVariable(named = ENV_VAR, matches = V2)
@TestCategories(@TestCategory(value = "Version2"))
class V2MethodTests extends H1 {

	Object student;

	@BeforeEach
	void setUp() {
		student = parameterless.instantiate();
	}

	@Test
	@Points(failDeduct = 3, category = "Version2")
	@DisplayName("setPersonID")
	void testSetPersonID() {
		String personId;

		personId = makePersonID('-');

		assertMatches(setPersonID.call(student, personId), OK_PATTERN,
			"Incorrect return value of setPersonID"
		);
		assertEquals(birthDate.read(student), personIdToBirthDate(personId),
				explain("Incorrect birthDate")
		);

		personId = makePersonID('+');

		assertMatches(setPersonID.call(student, personId), OK_PATTERN,
				"Incorrect return value of setPersonID"
		);
		assertEquals(birthDate.read(student), personIdToBirthDate(personId),
				explain("Incorrect birthDate")
		);

		personId = makePersonID('A');

		assertMatches(setPersonID.call(student, personId), OK_PATTERN,
				"Incorrect return value of setPersonID"
		);
		assertEquals(birthDate.read(student), personIdToBirthDate(personId),
				explain("Incorrect birthDate")
		);

		// The previous id with an extra character
		assertMatches(setPersonID.call(student, personId + randomLetter()),
				INVALID_BIRTHDAY_PATTERN, "Incorrect return value of setPersonID");

		// The same id but without the control character (one too short)
		assertMatches(setPersonID.call(student, personId.substring(0, 10)),
				INVALID_BIRTHDAY_PATTERN, "Incorrect return value of setPersonID");

		// The same id with an invalid checksum
		assertMatches(setPersonID.call(student, personId.substring(0, 10) + 'G'),
				INCORRECT_CHECK_MARK_PATTERN, "Incorrect return value of setPersonID");

		// A random id with an invalid century sign
		assertMatches(setPersonID.call(student, makePersonID('_')),
				INVALID_BIRTHDAY_PATTERN, "Incorrect return value of setPersonID");

		// Invalid argument
		assertMatches(setPersonID.call(student, randomString(11)),
				INVALID_BIRTHDAY_PATTERN, "Incorrect return value of setPersonID");

		// Invalid date
		Iterator<String> invalidDates = invalidDateGenerator();
		int i = 0;
		int j = rand.nextInt(32);
		String invalidDate = invalidDates.next();
		while (invalidDates.hasNext() && i < j) {
			invalidDate = invalidDates.next();
			i++;
		}

		String[] c = invalidDate.split("\\.");
		String dd = c[0];
		String mm = c[1];
		String yyyy = c[2];
		String yy = yyyy.substring(2);
		char checksum;

		char centurySign;
		int year = Integer.parseInt(yyyy);
		if (year >= 2000) {
			centurySign = 'A';
		} else if (year >= 1900) {
			centurySign = '-';
		} else {
			centurySign = '+';
		}

		String individualNumber = pad(rand.nextInt(898) + 2, 3);
		int remainder = Integer.parseInt(dd + mm + yy + individualNumber) % 31;
		checksum = "0123456789ABCDEFHJKLMNPRSTUVWXY".charAt(remainder);

		String invalid =
				dd + mm + yy + centurySign + individualNumber + checksum;

		assertMatches(setPersonID.call(student, invalid), INVALID_BIRTHDAY_PATTERN, 
				"Invalid return value of setPersonID");
	}

	@Test
	@Points(failDeduct = 3, category = "Version2")
	@DisplayName("checkPersonIDNumber")
	void testCheckPersonIDNumber() {
		String shouldReturnTrue = "checkPersonIDNumber should return true for a valid id: %s";
		String shouldReturnFalse = "checkPersonIDNumber should return false for an invalid id: %s";
		String id = makePersonID('-');

		assertReturns(checkPersonIDNumber.call(student, id), true,
				"checkPersonIDNumber", explain(String.format(shouldReturnTrue, id))
		);

		id = makePersonID('+');

		assertReturns(checkPersonIDNumber.call(student, id), true,
				"checkPersonIDNumber", explain(String.format(shouldReturnTrue, id))
		);

		id = makePersonID('A');

		assertReturns(checkPersonIDNumber.call(student, id), true,
				"checkPersonIDNumber", explain(String.format(shouldReturnTrue, id))
		);

		// A random id with an extra character
		id = makePersonID('-') + randomLetter();
		assertReturns(checkPersonIDNumber.call(student, id), false,
				"checkPersonIDNumber", explain(String.format(shouldReturnFalse, id))
		);

		// A random id without the control character (one character too short)
		id = makePersonID('-').substring(0, 10);
		assertReturns(checkPersonIDNumber.call(student, id), false,
				"checkPersonIDNumber", explain(String.format(shouldReturnFalse, id))
		);

		// A random id with an invalid century sign
		id = makePersonID('_');
		assertReturns(checkPersonIDNumber.call(student, id), false,
				"checkPersonIDNumber", explain(String.format(shouldReturnFalse, id))
		);
	}

	@Test
	@Points(failDeduct = 3, category = "Version2")
	@DisplayName("checkLeapYear")
	void testCheckLeapYear() {

		int[] leapYears =
				{1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936, 1940,
						1944, 1948, 1952, 1956, 1960, 1964, 1968, 1972, 1976,
						1980, 1984, 1988, 1992, 1996, 2000, 2004, 2008, 2012,
						2016, 2020, 2024, 2028, 2032, 2036, 2040, 2044, 2048,
						2052, 2056, 2060, 2064, 2068, 2072, 2076, 2080, 2084,
						2088, 2092, 2096, 2104, 2108, 2112, 2116, 2120, 2124,
						2128, 2132, 2136, 2140, 2144, 2148, 2152, 2156, 2160,
						2164, 2168, 2172, 2176, 2180, 2184, 2188, 2192, 2196,
						2204, 2208, 2212, 2216, 2220, 2224, 2228, 2232, 2236,
						2240, 2244, 2248, 2252, 2256, 2260, 2264, 2268, 2272,
						2276, 2280, 2284, 2288, 2292, 2296, 2304, 2308, 2312,
						2316, 2320, 2324, 2328, 2332, 2336, 2340, 2344, 2348,
						2352, 2356, 2360, 2364, 2368, 2372, 2376, 2380, 2384,
						2388, 2392, 2396, 2400, 2404, 2408, 2412, 2416, 2420,
						2424, 2428, 2432, 2436, 2440, 2444, 2448, 2452, 2456,
						2460, 2464, 2468, 2472, 2476, 2480, 2484, 2488, 2492,
						2496, 2504, 2508, 2512, 2516, 2520, 2524, 2528, 2532,
						2536, 2540, 2544, 2548, 2552, 2556, 2560, 2564, 2568,
						2572, 2576, 2580, 2584, 2588, 2592, 2596, 2604, 2608,
						2612, 2616, 2620, 2624, 2628, 2632, 2636, 2640, 2644,
						2648, 2652, 2656, 2660, 2664, 2668, 2672, 2676, 2680,
						2684, 2688, 2692, 2696, 2704, 2708, 2712, 2716, 2720,
						2724, 2728, 2732, 2736, 2740, 2744, 2748, 2752, 2756,
						2760, 2764, 2768, 2772, 2776, 2780, 2784, 2788, 2792,
						2796, 2800, 2804, 2808, 2812, 2816, 2820, 2824, 2828,
						2832, 2836, 2840, 2844, 2848, 2852, 2856, 2860, 2864,
						2868, 2872, 2876, 2880, 2884, 2888, 2892, 2896, 2904,
						2908, 2912, 2916, 2920, 2924, 2928, 2932, 2936, 2940,
						2944, 2948, 2952, 2956, 2960, 2964, 2968, 2972, 2976,
						2980, 2984, 2988, 2992, 2996};
		int[] notLeapYears =
				{1900, 1901, 1902, 1903, 1905, 1906, 1907, 1909, 1910, 1911,
						1913, 1914, 1915, 1917, 1918, 1919, 1921, 1922, 1923,
						1925, 1926, 1927, 1929, 1930, 1931, 1933, 1934, 1935,
						1937, 1938, 1939, 1941, 1942, 1943, 1945, 1946, 1947,
						1949, 1950, 1951, 1953, 1954, 1955, 1957, 1958, 1959,
						1961, 1962, 1963, 1965, 1966, 1967, 1969, 1970, 1971,
						1973, 1974, 1975, 1977, 1978, 1979, 1981, 1982, 1983,
						1985, 1986, 1987, 1989, 1990, 1991, 1993, 1994, 1995,
						1997, 1998, 1999, 2001, 2002, 2003, 2005, 2006, 2007,
						2009, 2010, 2011, 2013, 2014, 2015, 2017, 2018, 2019,
						2021, 2022, 2023, 2025, 2026, 2027, 2029, 2030, 2031,
						2033, 2034, 2035, 2037, 2038, 2039, 2041, 2042, 2043,
						2045, 2046, 2047, 2049, 2050, 2051, 2053, 2054, 2055,
						2057, 2058, 2059, 2061, 2062, 2063, 2065, 2066, 2067,
						2069, 2070, 2071, 2073, 2074, 2075, 2077, 2078, 2079,
						2081, 2082, 2083, 2085, 2086, 2087, 2089, 2090, 2091,
						2093, 2094, 2095, 2097, 2098, 2099, 2100, 2200, 2300,
						2500, 2600, 2700, 2900, 3000};
		ArrayList<Integer> wrongLeap = new ArrayList<>();
		ArrayList<Integer> wrongNotLeap = new ArrayList<>();

		for (int year : leapYears) {
			boolean leapYear = checkLeapYear.invoke(student, year);
			if (!leapYear) {
				wrongLeap.add(year);
			}
		}
		for (int year : notLeapYears) {
			boolean leapYear = checkLeapYear.invoke(student, year);
			if (leapYear) {
				wrongNotLeap.add(year);
			}
		}

		assertEquals(wrongLeap.size(), 0,
				explain("The following years were incorrectly "
						+ "not detected as leap years: " + wrongLeap)
		);
		assertEquals(wrongNotLeap.size(), 0,
				explain("The following years were incorrectly "
						+ "detected as leap years: " + wrongNotLeap)
		);
	}

	@Test
	@Points(failDeduct = 3, category = "Version2")
	@DisplayName("checkValidCharacter")
	void testCheckValidCharacter() {
		char[] invalidControlCharacters = { 'G', 'I', 'O', 'Q', 'Z' };
		for (int i = 0; i < 10; i++) {
			String validId = makePersonID('-');
			assertReturns(checkValidCharacter.call(student, validId),
					true, "checkValidCharacter",
					explain("checkValidCharacter should return true for valid id")
			);

			String idWithoutCC = validId.substring(0, 10); // Control character removed
			for (char invalid : invalidControlCharacters) {
				assertReturns(checkValidCharacter.call(student, idWithoutCC + invalid),
						false, "checkValidCharacter",
						explain("checkValidCharacter should return false for invalid id")
				);
			}
		}
	}

	@Test
	@Points(failDeduct = 3, category = "Version2")
	@DisplayName("checkBirthdate")
	void testCheckBirthDate() {
		// Valid dates
		for (int i = 0; i < 10; i++) {
			String date = randomDate();
			assertReturns(checkBirthdate.call(student, date),
					true, "checkBirthdate",
					explain("checkBirthdate should return true for valid date")
			);
		}
		// Invalid dates
		Iterator<String> invalidDates = invalidDateGenerator();
		while (invalidDates.hasNext()) {
			String date = invalidDates.next();
			assertReturns(checkBirthdate.call(student, date),
					false, "checkBirthdate",
					explain(String.format(
							"checkBirthdate should return false for invalid date \"%s\"", date))
			);
		}
	}

	static Iterator<String> invalidDateGenerator() {
		return new Iterator<>() {
			int day = 31;
			int month = 0;
			int year = rand.nextInt(1970, 2070);
			boolean dayNext = true;
			boolean leapYearsNext = false;
			boolean finished = false;
			final int[] leapYears =
					{1904, 1908, 1912, 1916, 1920, 1924, 1928, 1932, 1936,
							1940, 1944, 1948, 1952, 1956, 1960, 1964, 1968,
							1972, 1976, 1980, 1984, 1988, 1992, 1996, 2000,
							2004, 2008, 2012, 2016, 2020, 2024, 2028, 2032,
							2036, 2040, 2044, 2048, 2052, 2056, 2060, 2064,
							2068, 2072, 2076, 2080, 2084, 2088, 2092, 2096,
							2104};
			int leapYearIndex = 0;

			@Override
			public boolean hasNext() {
				if (finished) {
					// restart
					finished = false;
					day = 31;
					month = 0;
					year = rand.nextInt(1970, 2070);
					dayNext = true;
					leapYearsNext = false;
					leapYearIndex = 0;
					return false;
				}
				return true;
			}

			@Override
			public String next() {
				if (!leapYearsNext && !finished) {
					if (dayNext) {
						day = 0;
						dayNext = false;
					} else {
						if (month < 13) {
							month++;
						} else {
							leapYearsNext = true;
						}
						day = getDaysInMonth(month) + 1;
						dayNext = true;
					}
				} else {
					year = leapYears[leapYearIndex++];
					month = 2;
					day = 30;
					if (leapYearIndex == leapYears.length) {
						finished = true;
					}
				}

				return String.format("%02d.%02d.%d", day, month, year);
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			private int getDaysInMonth(int month) {
				switch (month) {
					case 0, 13, 1, 3, 5, 7, 8, 10, 12 -> {
						return 31;
					}
					case 4, 6, 9, 11 -> {
						return 30;
					}
					default -> {
						if (year % 4 == 0 && (year % 100 != 0
								|| year % 400 == 0)) {
							return 29;
						} else {
							return 28;
						}
					}
				}
			}
		};
	}
}
