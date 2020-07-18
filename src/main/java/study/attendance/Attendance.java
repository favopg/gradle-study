package study.attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 勤怠アプリ
 * @author イッシー
 *
 */
public class Attendance {

	/** 休憩時間が不要な労働時間 */
	private static final float ONE_REST_TIME_ACTUAL_WORK = 6F;
	/** 休憩時間が必要な労働時間 */
	private static final float TWO_REST_TIME_ACTUAL_WORK = 12F;
	/** 定時の場合の労働時間 */
	private static final float BASE_WORK = 8F;

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
	 * 指定月の勤怠一覧を取得する
	 * @param con コネクション
	 * @param yearMonth 対象年月
	 * @return 勤怠一覧
	 */
	public List<Map<AttendanceItem, Object>> getAttendance(Connection con, String yearAndMonth) {
		String getAttendanceSql = "select "
				+ "id, "
				+ "concat(year_and_month, day) as year_and_month_and_day, "
				+ "substring(start_hour_minute,1, 2) as start_hour, "
				+ "substring(start_hour_minute,3, 4) as start_minute, "
				+ "start_hour_minute, "
				+ "substring(end_hour_minute, 1, 2) as end_hour, "
				+ "substring(end_hour_minute, 3, 4) as end_minute, "
				+ "end_hour_minute, "
				+ "rest, "
				+ "over_work, "
				+ "created_at, "
				+ "updated_at  "
				+ "from appendance.attendance where year_and_month = ?";

		List<Map<AttendanceItem, Object>> attendanceList = new ArrayList<Map<AttendanceItem, Object>>();
		Map<AttendanceItem, Object> attendanceMap = null;

		try (PreparedStatement pstmt = con.prepareStatement(getAttendanceSql)) {
			pstmt.setString(1, yearAndMonth);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					attendanceMap = new HashMap<AttendanceItem, Object>();
					attendanceMap.put(AttendanceItem.id, rs.getInt("id"));
					attendanceMap.put(AttendanceItem.yearAndMonthAndDay, rs.getInt("year_and_month_and_day"));
					attendanceMap.put(AttendanceItem.startHourMinute, rs.getString("start_hour_minute"));
					attendanceMap.put(AttendanceItem.endHourMinute, rs.getString("end_hour_minute"));
					attendanceMap.put(AttendanceItem.rest, rs.getString("rest"));
					attendanceMap.put(AttendanceItem.overWork, rs.getString("over_work"));
					attendanceMap.put(AttendanceItem.createdAt, rs.getString("created_at"));
					attendanceMap.put(AttendanceItem.updatedAt, rs.getString("updated_at"));

					attendanceList.add(attendanceMap);
				}

			}

		} catch (SQLException e) {
			throw new RuntimeException("勤怠一覧取得SQL実行エラー", e.getCause());
		}

		return attendanceList;
	}

	/**
	 *
	 * @param attendanceInfo 日別の勤怠情報
	 * @return
	 */
	public int insertAttedance(Connection con, Map<AttendanceItem, Object> attendanceInfo) {
		String insertSql = "insert into appendance.attendance ("
				+ "year_and_month ,"
				+ "day, "
				+ "start_hour_minute, "
				+ "end_hour_minute, "
				+ "rest, "
				+ "over_work, "
				+ "created_at, "
				+ "updated_at"
				+ ") values("
				+ "?,"
				+ "?,"
				+ "?,"
				+ "?,"
				+ "?,"
				+ "?,"
				+ "now(),"
				+ "now()"
				+ ")";

		int insertResult = 0;

		try (PreparedStatement pstmt = con.prepareStatement(insertSql)) {
			pstmt.setString(1, attendanceInfo.get(AttendanceItem.yearAndMonth).toString());
			pstmt.setString(2, attendanceInfo.get(AttendanceItem.day).toString());
			pstmt.setInt(3, Integer.parseInt(attendanceInfo.get(AttendanceItem.startHourMinute).toString()));
			pstmt.setInt(4, Integer.parseInt(attendanceInfo.get(AttendanceItem.endHourMinute).toString()));
			pstmt.setFloat(5, Float.parseFloat(attendanceInfo.get(AttendanceItem.rest).toString()));
			pstmt.setFloat(6, Float.parseFloat(attendanceInfo.get(AttendanceItem.overWork).toString()));

			insertResult = pstmt.executeUpdate();

		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("勤務表データ登録エラー", e.getCause());
		}

		return insertResult;
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
	 * @param  労働時間
	 * @return 実労働時間
	 */
	public float calculateActualWork(float work) {
		return work - calculateRest(work);
	}

	/**
	 * 実労働時間から残業時間を算出する
	 * @param  実労働時間
	 * @return 残業時間
	 */
	public float calculateOverWork(float actualWork) {

		return actualWork - BASE_WORK;
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
