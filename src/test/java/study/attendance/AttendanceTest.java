package study.attendance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 勤怠アプリテストクラス
 * @author イッシー
 *
 */
class AttendanceTest {

	Attendance attendance = null;

	@BeforeEach
	void setUp() throws Exception {
		attendance = new Attendance();
	}

	/**
	 * 実労働が12時間の時に休憩時間は1時間となる
	 */
	@Test
	void testActualWorkTwelveWhenRestHourTwo() {
		short testData = 12;
		short expected = 2;
		short actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 実労働が6時間の時に休憩時間は1時間となる
	 */
	@Test
	void testActualWorkSixWhenRestHourOne() {
		short testData = 6;
		short expected = 1;
		short actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 実労働が5時間以下の時に休憩時間は0時間となる
	 */
	@Test
	void testActualWorkLessFiveWhenRestHourZero() {
		short testData = 5;
		short expected = 0;
		short actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);

	}

	/**
	 * マイナス値を渡した時にtrueで返却される
	 */
	@Test
	void testTimeIsMinusTrue() {
		short testData = -10;
		boolean expected = true;
		boolean actual = attendance.isMinus(testData);
		assertEquals(expected, actual);
	}

	/**
	 * プラス値を渡した時にfalseで返却される
	 */
	@Test
	void testTimeNotMinusFalse() {
		short testData = 10;
		boolean expected = false;
		boolean actual = attendance.isMinus(testData);
		assertEquals(expected, actual);
	}


}
