package databaseExercise7;

import java.sql.SQLException;
import javax.swing.JTable;

public class AccountManager {
	private DBHelper db = new DBHelper();
	public boolean transfer(Integer to, Integer from, Integer amount, JTable table){
		if(to == from){
			return true;
		}
		if(amount < 0){
			return false;
		}
		if(getRowByValue(table, to) == -1 || getRowByValue(table, from) == -1){
			return false;
		}

		Integer toOriginalAmount = (Integer) table.getValueAt(getRowByValue(table, to), 2);
		Integer fromOriginalAmount = (Integer) table.getValueAt(getRowByValue(table, from),  2);
		
		if(transferCanBeDone(fromOriginalAmount, amount)){
			Integer final1 = fromOriginalAmount - amount;
			Integer final2 = toOriginalAmount + amount;
			try {
				db.transact(db.updateSQL(final1, from),db.updateSQL(final2, to));
				return true;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} 
		return false;
	}
	
	public Object[][] getData(){
		try {
			return db.select();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return new Object[0][];
		}
	}
	
	public int getRowByValue(JTable model, Object value) {
	    for (int i = model.getRowCount() - 1; i >= 0; --i) {
            if (model.getValueAt(i, 0).equals(value)) {
                return i;
            }
	    }
	    return -1;
	 }
	
	public boolean transferCanBeDone(Integer from, Integer amount){
		return from - amount > 0;
	}
}
