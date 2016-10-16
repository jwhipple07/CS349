package photoViewer;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DBHelper {
	private Connection con;
	private Statement stmt;

	public DBHelper() {
		try {
			initConnection();
			if (!tableExists("photos")) {
				createTable();
			}
			if (!tableExists("Photo_List_Order")) {
				createOrderTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createOrderTable() throws SQLException {
		stmt.executeUpdate(Repository.createOrderTable);
	}

	private void createTable() throws SQLException {
		stmt.executeUpdate(Repository.createTable);
	}

	private void initConnection() throws Exception {
		Class.forName(Repository.driver);
		con = DriverManager.getConnection(Repository.URLdb, Repository.mysqluserID, Repository.mysqlpassword);
		stmt = con.createStatement();
	}

	private boolean tableExists(String tablename) throws SQLException {
		DatabaseMetaData dbmd;
		ResultSet rs;
		dbmd = con.getMetaData();
		rs = dbmd.getTables(null, null, tablename, null);
		if (rs.next()) {
			return true;
		}
		return false;
	}

	public boolean deletePhoto(Photo photo) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(Repository.deletePhoto);
			pstmt.setInt(1, photo.id);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Integer getActivePhotoIndex(){
		ResultSet rs = null;
		Integer index = 0;
		try {
			rs = stmt.executeQuery(Repository.selectActivePhoto);
			while (rs.next()) {
				index = rs.getInt("photo_order");
				return index;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return index;
		}
		return index;
	}

	public Integer getCount() {
		ResultSet rs = null;
		Integer count = 0;
		try {
			rs = stmt.executeQuery(Repository.selectPhotoCount);
			while (rs.next()) {
				count = rs.getInt("count");
				return count;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return count;
	}

	public Photo getLastPhoto() {
		Photo nextPhoto = new Photo();
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(Repository.selectLastPhoto); // execute prepared statement

			while (rs.next()) {
				nextPhoto.description = rs.getString("photo_description");
				nextPhoto.date = rs.getDate("photo_date");
				nextPhoto.id = rs.getInt("photo_id");
				nextPhoto.imageByte = rs.getBytes("photo");
				nextPhoto.image = new ImageIcon(nextPhoto.imageByte);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return nextPhoto;
	}

	public List<Integer> getOrder() {
		List<Integer> orders = new ArrayList<Integer>();
		try {
			ResultSet rs = stmt.executeQuery(Repository.selectOrder);
			while (rs.next()) {
				Integer id = rs.getInt("photo_id");
				Integer index = rs.getInt("photo_order");
				orders.add(index, id);
			}
		} catch (SQLException e) {
			return null;
		}
		return orders;
	}

	public Photo getPhoto(Integer id) {
		Photo nextPhoto = new Photo();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = con.prepareStatement(Repository.selectPhoto);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery(); // execute prepared statement

			while (rs.next()) {
				nextPhoto.description = rs.getString("photo_description");
				nextPhoto.date = rs.getDate("photo_date");
				nextPhoto.id = id;
				nextPhoto.imageByte = rs.getBytes("photo");
				nextPhoto.image = new ImageIcon(nextPhoto.imageByte);
			}
			pstmt.close();
		} catch (SQLException e) {
			return nextPhoto;
		}

		return nextPhoto;
	}

	public Photo putImage(Photo photo) {
		PreparedStatement pstmt = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(photo.imageByte);
			pstmt = con.prepareStatement(Repository.insertPhoto);
			pstmt.setString(1, photo.description);
			pstmt.setDate(2, photo.date);
			pstmt.setBinaryStream(3, bis, (int) photo.imageByte.length);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return getLastPhoto();
	}

	public void saveOrder(List<Integer> order) {
		try {
			stmt.executeUpdate(Repository.deleteAllOrder);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < order.size(); i++) {
			try {
				PreparedStatement pstmt = null;
				pstmt = con.prepareStatement(Repository.insertOrder);
				pstmt.setInt(1, order.get(i));
				pstmt.setInt(2, i);
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {

			}
		}
	}

	public void setActivePhoto(Photo photo){
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(Repository.setActivePhotoToFalse);
			pstmt.executeUpdate();
			pstmt = con.prepareStatement(Repository.updateActivePhoto);
			pstmt.setInt(1, photo.id);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean updatePhoto(Photo photo) {
		PreparedStatement pstmt = null;
		try {

			pstmt = con.prepareStatement(Repository.updatePhoto);
			//ByteArrayInputStream bis = new ByteArrayInputStream(photo.imageByte);

			pstmt.setString(1, photo.description);
			pstmt.setDate(2, photo.date);
			//pstmt.setBinaryStream(3, bis, (int) photo.imageByte.length);
			pstmt.setInt(3, photo.id);
			pstmt.executeUpdate(); // execute prepared statement
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}