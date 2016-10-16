package photoViewer;

public class Repository {
	public final static String createOrderTable =  "create table Photo_List_Order (photo_id INT NOT NULL, photo_order INT NOT NULL)";
	public final static String createTable = "create table Photos (photo_id INT NOT NULL AUTO_INCREMENT, "
	+ "photo_description VARCHAR(60), photo_date DATE NOT NULL, photo LONG VARBINARY NOT NULL, "
	+ "active_photo BOOLEAN default FALSE,"
	+ "original_dttm DATETIME DEFAULT CURRENT_TIMESTAMP, PRIMARY KEY (photo_id))";
	public final static String deleteAllOrder = "delete from Photo_List_Order";
	public final static String deletePhoto = "delete from photos where photo_id = ?";
	public final static String driver = "com.mysql.jdbc.Driver";
	public final static String insertOrder = "insert into Photo_List_Order values ( ?, ? )";
	public final static String insertPhoto = "insert into photos(photo_description, photo_date, photo) values (?, ?, ?)";
	public final static String mysqluserID = "jmwcn7";
	public final static String mysqlpassword = "V8jPNBP3NR";
	public final static String selectLastPhoto = "select * from photos order by original_dttm desc limit 1";
	public final static String selectOrder = "select photo_id, photo_order from Photo_List_Order";
	public final static String selectPhotoCount = "select count(*) as count from photos";
	public final static String selectPhoto = "select photo_id, photo_description, photo_date, photo from photos where photo_id = ?";
	public final static String selectActivePhoto = "select photo_order from Photo_List_order a, photos b where a.photo_id = b.photo_id and active_photo = true limit 1";
	public final static String setActivePhotoToFalse = "update photos set active_photo = false";
	public final static String updatePhoto = "update photos set photo_description = ?, photo_date = ? where photo_id = ?";
	public final static String updateActivePhoto = "update photos set active_photo = true where photo_id = ?";
	public final static String URLdb = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/" + mysqluserID;

}
