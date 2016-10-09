package databaseExercise7;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
	
    private final static String mysqluserID    = "jmwcn7";
    private final static String mysqlpassword  = "V8jPNBP3NR";
	private final static String dbURL = "jdbc:mysql://kc-sce-appdb01.kc.umkc.edu/"
			+ mysqluserID;
    private final static String driver = "com.mysql.jdbc.Driver";
    private String updateURL = "update account set account_balance = %s where account_id = %s";
    private Connection con;
    private Statement stmt;
    
    public DBHelper() {
    	try {
    		initConnection();
			if (!tableExists()){
				createTable();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public String updateSQL(Integer amount, Integer id){
    	return String.format(updateURL, amount, id);
    }
    
    private void initConnection() throws Exception {
        Class.forName(driver);
        con = DriverManager.getConnection(dbURL,mysqluserID,mysqlpassword);
        stmt = con.createStatement();
    }

    private void deleteTable() throws SQLException {
        String sqlCmd = "DROP TABLE account";
        stmt.executeUpdate(sqlCmd);
	}
    
	private void createTable() throws SQLException {
        String createSQL = "create table Account (account_id INT NOT NULL AUTO_INCREMENT, account_name VARCHAR(24), account_balance INT NOT NULL, PRIMARY KEY (account_id))";
        stmt.executeUpdate(createSQL);
	}

	private boolean tableExists() throws SQLException {
		DatabaseMetaData dbmd;
		ResultSet rs;
		dbmd = con.getMetaData();
		rs = dbmd.getTables(null, null, "account", null);
		if (rs.next()) {
			return true;
		}
		return false;
	}
	
	public Object[][] select() throws SQLException{
		ResultSet rs = stmt.executeQuery("select * from account");
		List<Object[]> rows = new ArrayList<Object[]>();
		Object[] row;
		while(rs.next()){
			String accountName;
			Integer id, amount;
			id = rs.getInt(1); 
			accountName = rs.getString(2);
			amount = rs.getInt(3);
			row = new Object[] {id, accountName, amount};	
			rows.add(row);
		}
		Object[][] results = new Object[rows.size()][3];
		for(int i = 0; i < rows.size(); i++){
			results[i] = rows.get(i);
		}
		return results;
		
	}
    
	public boolean transact(String...SQLs) throws Exception{
		try {
			con.setAutoCommit(false);
			for(String SQL : SQLs){
				stmt.executeUpdate(SQL);
			}			
			con.commit();
		}
		catch (Exception e) {
			System.out.println("Error during transaction:" + e);
			con.rollback();
		}
		con.setAutoCommit(true);
		return true;
	}

}