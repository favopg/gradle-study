package study.attendance;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 勤怠アプリ
 * @author イッシー
 *
 */
public class Attendance {

	private static final float ONE_REST_TIME_ACTUAL_WORK = 6F;
	private static final float TWO_REST_TIME_ACTUAL_WORK = 12F;

	public Attendance() {

	}

	/**
	 * 月末日を取得する
	 * @return 月末日
	 */
	public int getLastDay() {
		LocalDate nowDate = LocalDate.now();
		return Integer.parseInt(String.valueOf(nowDate.with(TemporalAdjusters.lastDayOfMonth())).split("-")[2]);
	}

	/**
	 * 現在年月をyyyymm形式で取得する
	 * @return yyyymm
	 */
	public String getNowYearAndMonth() {
		LocalDate nowDate = LocalDate.now();
		return String.valueOf(nowDate.getDayOfYear()) + String.valueOf(nowDate.getDayOfMonth());
	}

	/**
	 * 勤怠テンプレートを取得する
	 * @return 勤怠情報
	 */
	public Map<AttendanceItem, Object> getTemplateAttendance(int templateId) {
		// TODO データベースから取得する いったんはダミーデータで作成
		Map<AttendanceItem, Object> attendanceInfo = new HashMap<AttendanceItem, Object>();
		return attendanceInfo;
	}

	/**
	 * 出社時間、退勤時間から労働時間を算出する、
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @return 実労働時間
	 */
	public float calculateWork(short startHour, short startMinute, short endHour, short endMinute) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		Calendar actualWork = Calendar.getInstance();
		SimpleDateFormat timerFormat = new SimpleDateFormat("HH:mm");

		start.set(Calendar.HOUR_OF_DAY, startHour);
		start.set(Calendar.MINUTE, startMinute);

		end.set(Calendar.HOUR_OF_DAY, endHour);
		end.set(Calendar.MINUTE, endMinute);

		long timeInMillis = end.getTimeInMillis() - start.getTimeInMillis() - actualWork.getTimeZone().getRawOffset();
		actualWork.setTimeInMillis(timeInMillis);

		String[] hourAndMinute = timerFormat.format(actualWork.getTime()).split(":");

		float hour = Float.parseFloat(hourAndMinute[0]);
		float minute = Float.parseFloat(hourAndMinute[1]) / 60F;

		return hour + minute;
	}

	/**
	 * 実労働時間を算出する
	 * @param work 労働時間
	 * @return 実労働時間
	 */
	public float calculateActualWork(float work) {
		return work - calculateRest(work);
	}

	/**
	 * 労働時間から休憩時間を算出する
	 * @param actualWork 労働時間
	 * @return 休憩時間
	 */
	public float calculateRest(float work) {

		if (ONE_REST_TIME_ACTUAL_WORK <= work && TWO_REST_TIME_ACTUAL_WORK > work) {
			return 1F;
		} else if (TWO_REST_TIME_ACTUAL_WORK <= work) {
			return 2F;
		}

		return 0F;
	}

	/**
	 * 0より小さいかチェックする
	 * @param time 時間
	 * @return true:0以下 false：0より大きい
	 */
	public boolean isMinus(float time) {
		if (time <= 0) {
			return true;
		}

		return false;
	}

}
