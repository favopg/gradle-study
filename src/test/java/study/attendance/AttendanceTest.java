package study.attendance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
	 * 実労働が12時間以上の場合は休憩時間が2時間となる
	 */
	@Test
	void testActualWorkTwelveWhenRestHourTwo() {
		float testData = 12;
		float expected = 2;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 実労働が6時間～11時間の場合は休憩時間が1時間となる
	 */
	@ParameterizedTest
	@ValueSource(floats = {6,7,8,9,10,11})
	void testActualWorkSixWhenRestHourOne(float testData) {
		float expected = 1F;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 実労働が5時間以下の時に休憩時間は0時間となる
	 */
	@ParameterizedTest
	@ValueSource(shorts = {0,1,2,3,4,5})
	void testActualWorkLessFiveWhenRestHourZero(short testData) {
		float expected = 0F;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);

	}

	/**
	 * 負の値を渡した時にtrueで返却される
	 */
	@ParameterizedTest
	@ValueSource(shorts = {-1,0,-100,-1000})
	void testTimeIsMinusTrue(short testData) {
		boolean expected = true;
		boolean actual = attendance.isMinus(testData);
		assertEquals(expected, actual);

	}

	/**
	 * 正の値を渡した時にfalseで返却される
	 */
	@ParameterizedTest
	@ValueSource(shorts = {1,10,100,1000})
	void testTimeNotMinusFalse(short testData) {
		boolean expected = false;
		boolean actual = attendance.isMinus(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 10時00分出勤、19時00分退勤の時に実働が9時間となる
	 */
	@Test
	void testCalculateActualWork() {
		short startHour = 10;
		short startMinute = 00;
		short endHour = 19;
		short endMinute = 00;

		float expected = 9F;
		float actual = attendance.calculateActualWork(startHour, startMinute, endHour, endMinute);
		assertEquals(expected, actual);

	}
}
