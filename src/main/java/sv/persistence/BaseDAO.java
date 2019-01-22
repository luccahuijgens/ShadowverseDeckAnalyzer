package sv.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BaseDAO {
	
	public BaseDAO() {}

	protected final Connection getConnection() {
	    Connection result = null;

	    try {
	      InitialContext ic = new InitialContext();
	      DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
	      
	      result = ds.getConnection();
	    } catch (Exception ex) {
	      throw new RuntimeException(ex);
	    }

	    return result;
	  }

}
