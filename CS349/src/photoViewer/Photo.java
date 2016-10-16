package photoViewer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

import javax.swing.ImageIcon;

public class Photo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8564932653305439906L;
	public String description;
	public Date date;
	public String dateText;
	public ImageIcon image;
	public Integer id;
	public byte[] imageByte;
	private String pattern = "yyyy-MM-dd";
	private SimpleDateFormat format = new SimpleDateFormat(pattern);

	public Photo() {
		description = "";
		date =  new Date(Calendar.getInstance().getTimeInMillis());
		dateText = format.format(date);
		image = new ImageIcon("Empty");
		imageByte = new byte[0];
	}
	/**
	 *  This will parse until pattern is met<br>
	 *  i.e. 2016-12-31gt will be accepted but formatted to 2016-12-31
	 * @param dateToValidate - string that was saved
	 * @return
	 */
	public boolean validateDate(String dateToValidate){

		try {
			java.util.Date parsed = format.parse(dateToValidate);
			date = new java.sql.Date(parsed.getTime());
			dateText = format.format(date); //this will only happen if it was parsed
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
