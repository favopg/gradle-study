package study.attendance;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

/**
 * 勤怠アプリテストクラス
 * @author イッシー
 *
 */
class AttendanceTest extends BaseDb{

	private Attendance attendance = null;
	@Mock
	private Attendance mockAttendance;

	@BeforeEach
	void setUp() throws Exception {
		attendance = new Attendance();
	}

	@Test
	void testInsertData() throws Exception{
		insertTestData(getIDatabaseConnection(getConnection()));
	}

	/**
	 * 勤怠テンプレートを取得できる
	 */
	@Test
	void testGetTemplateAttendance() {
		// TODO Mockで作成しているので実装した後にテストやり直す

		Map<AttendanceItem, Object> expected = new HashMap<AttendanceItem, Object>();
		expected.put(AttendanceItem.templateId, 1);
		expected.put(AttendanceItem.workId, 1);
		expected.put(AttendanceItem.yearAndMonthAndDay, 20200701);
		expected.put(AttendanceItem.startHour, 10);
		expected.put(AttendanceItem.startMinute, 00);
		expected.put(AttendanceItem.endHour, 19);
		expected.put(AttendanceItem.endMinute, 00);
		expected.put(AttendanceItem.actualWork, 8);
		expected.put(AttendanceItem.rest, 1);
		expected.put(AttendanceItem.overWork, 0);
		expected.put(AttendanceItem.nightOverWork, 0);

		mockAttendance = mock(Attendance.class);
		mockAttendance.getTemplateAttendance(1);
		when(mockAttendance.getTemplateAttendance(1)).thenReturn(expected);

		assertEquals(expected, mockAttendance.getTemplateAttendance(1));
	}

	/**
	 * 今月末日を取得できる
	 */
	@Test
	void testGetLastDay() {
		LocalDate nowDate = LocalDate.now();
		int expected = Integer.parseInt(String.valueOf(nowDate.with(TemporalAdjusters.lastDayOfMonth())).split("-")[2]);
		int actual = attendance.getLastDay();

		assertEquals(expected, actual);
	}

	/**
	 * 今日の年月を取得できる
	 */
	@Test
	void testGetNowYearAndMonth() {
		LocalDate nowDate = LocalDate.now();
		String expected = String.valueOf(nowDate.getDayOfYear()) + String.valueOf(nowDate.getDayOfMonth());
		String actual = attendance.getNowYearAndMonth();

		assertEquals(expected, actual);

	}

	/**
	 * 労働が12時間以上の場合は休憩時間が2時間となる
	 */
	@Test
	void testActualWorkTwelveWhenRestHourTwo() {
		float testData = 12;
		float expected = 2;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 労働が6時間～11時間の場合は休憩時間が1時間となる
	 */
	@ParameterizedTest
	@ValueSource(floats = { 6F, 7F, 8F, 9F, 10F, 11F })
	void testActualWorkSixWhenRestHourOne(float testData) {
		float expected = 1F;
		float actual = attendance.calculateRest(testData);
		assertEquals(expected, actual);
	}

	/**
	 * 労働が5時間以下の時に休憩時間は0時間となる
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
	 * 10時00分出勤、19時00分退勤の時に労働が9時間となる
	 */
	@Test
	void testCalculateWork() {
		short startHour = 10;
		short startMinute = 00;
		short endHour = 19;
		short endMinute = 00;

		float expected = 9F;
		float actual = attendance.calculateWork(startHour, startMinute, endHour, endMinute);
		assertEquals(expected, actual);

	}

	/**
	 * 10時00分出勤、19時30分退勤の時に労働が9.5時間となる
	 */
	@Test
	void testCalculateWorkFloat() {
		short startHour = 10;
		short startMinute = 00;
		short endHour = 19;
		short endMinute = 30;

		float expected = 9.5F;
		float actual = attendance.calculateWork(startHour, startMinute, endHour, endMinute);
		assertEquals(expected, actual);
	}


	/**
	 * 労働時間が9時間の時に実労働時間が8時間となる
	 */
	@Test
	void testNineWorkWhenActualWorkEight() {
		float testData = 9F;
		float expected = 8F;
		float actual = attendance.calculateActualWork(testData);

		assertEquals(expected, actual);
	}

	/**
	 * 労働時間が5時間の時に実労働時間が5時間となる
	 */
	@Test
	void testFiveWorkWhenActualWorkFive() {
		float testData = 5F;
		float expected = 5F;
		float actual = attendance.calculateActualWork(testData);

		assertEquals(expected, actual);
	}

	/**
	 * 労働時間が12時間の時に実労働時間が10時間となる
	 */
	@Test
	void testTwelveWorkWhenActuaWorkTen() {
		float testData = 12F;
		float expected = 10F;
		float actual = attendance.calculateActualWork(testData);

		assertEquals(expected, actual);
	}

	/**
	 * 労働時間が9.5時間の時に実労働時間が8.5時間となる
	 */
	@Test
	void testNineHalfWhenActualWorkEightHalf() {
		float testData = 9.5F;
		float expected = 8.5F;
		float actual = attendance.calculateActualWork(testData);

		assertEquals(expected, actual);
	}

	/**
	 * 実労働時間が8時間の時に残業時間が0時間となる
	 */
	@Test
	void testActualWorkEightWhenOverWorkZero() {
		float testData = 8F;
		float expected = 0F;
		float actual = attendance.calculateOverWork(testData);

		assertEquals(expected, actual);

	}

	/**
	 * 実労働時間が10時間の時に残業時間が2時間となる
	 */
	void testActualWorkTenWhenOverWorkTwo() {
		float testData = 10F;
		float expected = 2F;
		float actual = attendance.calculateOverWork(testData);

		assertEquals(expected, actual);

	}

}
