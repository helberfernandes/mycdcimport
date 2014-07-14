package br.com.wofsolutions.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoUtil implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -746092924167391159L;
	private static Connection con;
	
	
	public  ConexaoUtil() {
		super();
	}

	public static void conecta(){
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			con = DriverManager
					.getConnection("jdbc:mysql://localhost/wofcdc?autoReconnect=true",
							"root", "st1215");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection getCon() {
		if(con==null){
			conecta();
		} else
			try {
				if(con.isClosed()){
					conecta();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

}
