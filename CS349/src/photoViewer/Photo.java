package photoViewer;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public Integer index;
	private String pattern = "MM/dd/yyyy";
	private SimpleDateFormat format = new SimpleDateFormat(pattern);

	public Photo() {
		description = "";
		date = new Date();
		dateText = format.format(date);
		image = new ImageIcon("");
		index = 0;
	}
	/**
	 *  This will parse until pattern is met<br>
	 *  i.e. 01/02/2016gt will be accepted but formatted to 01/02/2016
	 * @param dateToValidate - string that was saved
	 * @return
	 */
	public boolean validateDate(String dateToValidate){

		try {
			date = format.parse(dateToValidate);
			dateText = format.format(date); //this will only happen if it was parsed
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
