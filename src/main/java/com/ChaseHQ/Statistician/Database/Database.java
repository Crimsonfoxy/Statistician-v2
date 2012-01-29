package com.ChaseHQ.Statistician.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ChaseHQ.Statistician.Log;
import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.DataValues.DataValues_Config;

public class Database {
	private static Database _singletonDB = null;

	private Connection connection = null;

	static public Database getDB() {
		if (Database._singletonDB == null) {
			try {
				new Database();
			} catch (ClassNotFoundException e) {
				Log.ConsoleLog("Critical Error, mySQL Driver not found. Is this the latest version of CraftBukkit?!");
			} catch (DBConnectFail e) {
				Log.ConsoleLog("Critical Error, could not connect to mySQL. Is the database Available? Check config file and try again.");
			}
		}

		return Database._singletonDB;
	}

	public Database() throws ClassNotFoundException, DBConnectFail {
		if (Database._singletonDB != null) return;

		// Connect To DB And Hold info
		Class.forName("com.mysql.jdbc.Driver");
		this.ConnectToDB();
		new DBConfig(this.connection);

		Database._singletonDB = this;

		DataValues_Config.refreshConfigValues();
	}

	private void ConnectToDB() throws DBConnectFail {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + Config.getConfig().getDBAddress() + ":" + Config.getConfig().getDBPort() + "/" + Config.getConfig().getDBName(),
					Config.getConfig().getDBUsername(), Config.getConfig().getDBPassword());
		} catch (SQLException e) {
			throw new DBConnectFail();
		}
	}

	public boolean executeSynchUpdate(String theQuery) {
		int rowsChanged = 0;

		try {
			rowsChanged = this.connection.createStatement().executeUpdate(theQuery);
		} catch (SQLException e) {
			Log.ConsoleLog(theQuery + " :: Update Failed, Checking Connection");
			this.checkConnectionTryReconnect();
			return false;
		}

		if (rowsChanged > 0)
			return true;
		return false;
	}

	public List<Map<String, String>> executeSynchQuery(String theQuery) {
		List<Map<String, String>> ColData = new ArrayList<Map<String, String>>();

		try {
			Statement stmnt = this.connection.createStatement();
			ResultSet rs = stmnt.executeQuery(theQuery);
			int Row = 0;
			while (rs.next()) {
				HashMap<String, String> rowToAdd = new HashMap<String, String>();
				for (int x = 1; x <= rs.getMetaData().getColumnCount(); ++x) {
					rowToAdd.put(rs.getMetaData().getColumnName(x), rs.getString(x));
				}
				ColData.add(rowToAdd);
				++Row;
			}
		} catch (SQLException e) {
			Log.ConsoleLog(theQuery + " :: Query Failed, Checking Connection");
			this.checkConnectionTryReconnect();
			return null;
		}
		return ColData;
	}

	public boolean callStoredProcedure(String procName, List<String> variables) {
		String storedProcCall = "CALL " + Config.getConfig().getDBName() + "." + procName + "(";
		if (variables != null) {
			Iterator<String> itr = variables.iterator();
			while (itr.hasNext()) {
				String thisVariable = itr.next();
				storedProcCall += "'" + thisVariable + "',";
			}
			storedProcCall = storedProcCall.substring(0, storedProcCall.length() - 1);
		}
		storedProcCall += ");";
		try {
			this.connection.createStatement().executeUpdate(storedProcCall);
		} catch (SQLException e) {
			Log.ConsoleLog(storedProcCall + " :: Stored Procedure Failed, Checking Connection");
			this.checkConnectionTryReconnect();
			return false;
		}

		return true;
	}

	private void checkConnectionTryReconnect() {
		try {
			if (this.connection.isValid(10)) {
				Log.ConsoleLog("Connection is still present... It may of been a malformed Query ?");
			} else {
				Log.ConsoleLog("Connection has been lost with Database, Attempting Reconnect.");
				try {
					this.connection = DriverManager.getConnection("jdbc:mysql://" + Config.getConfig().getDBAddress() + ":" + Config.getConfig().getDBPort() + "/" + Config.getConfig().getDBName(),
							Config.getConfig().getDBUsername(), Config.getConfig().getDBPassword());
					Log.ConsoleLog("Connection to the database re-established, We lost some stats though :(");
				} catch (SQLException connect_e) {
					Log.ConsoleLog("Could Not Reconnect :( Stats are going to be lost :(");
				}
			}
		} catch (SQLException e) {
			Log.ConsoleLog("Connection has been lost with Database, Attempting Reconnect.");
			try {
				this.connection = DriverManager.getConnection("jdbc:mysql://" + Config.getConfig().getDBAddress() + ":" + Config.getConfig().getDBPort() + "/" + Config.getConfig().getDBName(),
						Config.getConfig().getDBUsername(), Config.getConfig().getDBPassword());
				Log.ConsoleLog("Connection to the database re-established, We lost some stats though :(");
			} catch (SQLException connect_e) {
				Log.ConsoleLog("Could Not Reconnect :( Stats are going to be lost :(");
			}
		}
	}
}
