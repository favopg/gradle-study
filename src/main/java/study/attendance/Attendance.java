package study.attendance;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * 勤怠アプリ
 * @author イッシー
 *
 */
public class Attendance {

	private static final float ONE_REST_TIME_ACTUAL_WORK = 5F;
	private static final float TWO_REST_TIME_ACTUAL_WORK = 11F;

	public Attendance() {

	}

	/**
	 * 現在年月を取得する
	 * @return
	 */
	public String getNowYearAndMonth() {

		return "";
	}

	/**
	 * 勤怠テンプレートを取得する
	 * @return
	 */
	public Map<String, String> getTemplateAttendance() {
		// TODO データベースから取得する

		return null;
	}

	/**
	 * 出社時間、退勤時間から実労働時間を算出する、
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @return 実労働時間
	 */
	public float calculateActualWork(short startHour, short startMinute, short endHour, short endMinute) {
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
		float hour = Integer.parseInt(hourAndMinute[0]);
		float minute = Integer.parseInt(hourAndMinute[1]) / 60;

		return hour + minute;
	}

	/**
	 * 実労働時間から休憩時間を算出する
	 * @param actualWork 実労働
	 * @return 休憩時間
	 */
	public float calculateRest(float actualWork) {

		if (ONE_REST_TIME_ACTUAL_WORK < actualWork && TWO_REST_TIME_ACTUAL_WORK >= actualWork) {
			return 1F;
		} else if (TWO_REST_TIME_ACTUAL_WORK < actualWork) {
			return 2F;
		}

		return 0F;
	}

	/**
	 * 0より小さいかチェックする
	 * @param time 時間
	 * @return true:0以下 false：0より大きい
	 */
	public boolean isMinus(short time) {
		if (time <= 0) {
			return true;
		}

		return false;
	}

}
