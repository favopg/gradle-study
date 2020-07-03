package study.attendance;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

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
	 * 今月末日を取得する
	 */
	@Test
	void testGetLastDay() {
		LocalDate nowDate = LocalDate.now();
		int expected = Integer.parseInt(String.valueOf(nowDate.with(TemporalAdjusters.lastDayOfMonth())).split("-")[2]);
		int actual = attendance.getLastDay();

		assertEquals(expected, actual);
	}

	/**
	 * 今日の年月を取得する
	 */
	@Test
	void testNowYearAndMonth() {
		LocalDate nowDate = LocalDate.now();
		String expected = String.valueOf(nowDate.getDayOfYear()) + String.valueOf(nowDate.getDayOfMonth());
		String actual = attendance.getNowYearAndMonth();

		assertEquals(expected, actual);

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
	@ValueSource(floats = { 6F, 7F, 8F, 9F, 10F, 11F })
	void testActualWorkSixWhenRestHourOne(float testData) {
		float expected = 1F;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 実労働が5時間以下の時に休憩時間は0時間となる
	 */
	@ParameterizedTest
	@ValueSource(floats = { 0F, 1F, 2F, 3F, 4F, 5F })
	void testActualWorkLessFiveWhenRestHourZero(float testData) {
		float expected = 0F;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);

	}

	/**
	 * 負の値を渡した時にtrueで返却される
	 */
	@ParameterizedTest
	@ValueSource(floats = { -1F, 0F, -100F, -1000F })
	void testTimeIsMinusTrue(float testData) {
		boolean expected = true;
		boolean actual = attendance.isMinus(testData);
		assertEquals(expected, actual);

	}

	/**
	 * 正の値を渡した時にfalseで返却される
	 */
	@ParameterizedTest
	@ValueSource(floats = { 1F, 10F, 100F, 1000F })
	void testTimeNotMinusFalse(float testData) {
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

	/**
	 * 10時00分出勤、19時30分退勤の時に実働が9.5時間となる
	 */
	@Test
	void testCalculateActualWorkFloat() {
		short startHour = 10;
		short startMinute = 00;
		short endHour = 19;
		short endMinute = 30;

		float expected = 9.5F;
		float actual = attendance.calculateActualWork(startHour, startMinute, endHour, endMinute);
		assertEquals(expected, actual);
	}

}
