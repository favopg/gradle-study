package study.attendance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendanceTest {

	Attendance attendance = null;

	@BeforeEach
	void setUp() throws Exception {
		attendance = new Attendance();
	}

	/**
	 * 実労働が12時間の時に休憩時間は２時間となる
	 */
	@Test
	void test() {
		short testData = 12;
		short expected = 2;
		short actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}



}
