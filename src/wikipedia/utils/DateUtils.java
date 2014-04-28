package wikipedia.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author Mir4ik
 * @version 0.1 27.02.2014
 */
public class DateUtils {

    private static final String[] MONTH = {"січня", "лютого", "березня",
    	"квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня",
    	"листопада", "грудня"};

	private DateUtils() {}

	public static String convertDate(long time) {
		if (time < 0) {
			throw new IllegalArgumentException("Time can not be negative!");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(time));
		StringBuilder sb = new StringBuilder();
		sb.append(calendar.get(Calendar.DAY_OF_MONTH));
		sb.append(' ');
		sb.append(MONTH[calendar.get(Calendar.MONTH)]);
		sb.append(' ');
		sb.append(calendar.get(Calendar.YEAR));
		return sb.toString();
	}
	
	public static boolean month(String month) {
		for (int i = 0; i < MONTH.length; i++) {
			if (MONTH[i].equalsIgnoreCase(month)) {
				return true;
			}
		}
		return false;
	}
}