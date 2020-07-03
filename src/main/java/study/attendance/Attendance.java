package study.attendance;

import java.util.Calendar;
import java.util.Map;

/**
 * 勤怠アプリ
 * @author イッシー
 *
 */
public class Attendance {

	private static final short ONE_REST_TIME_ACTUAL_WORK = 5;
	private static final short TWO_REST_TIME_ACTUAL_WORK = 11;

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
	 * @return
	 */
	public short calculateActualWork(short startHour, short startMinute, short endHour, short endMinute) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();

		return 0;
	}

	/**
	 * 実労働時間から休憩時間を算出する
	 * @param actualWork 実労働
	 * @return 休憩時間
	 */
	public short calculateRest(short actualWork) {

		if (ONE_REST_TIME_ACTUAL_WORK < actualWork && TWO_REST_TIME_ACTUAL_WORK >= actualWork) {
			return 1;
		} else if (TWO_REST_TIME_ACTUAL_WORK < actualWork) {
			return 2;
		}

		return 0;
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
