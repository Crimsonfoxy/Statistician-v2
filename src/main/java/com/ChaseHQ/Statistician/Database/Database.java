package com.ChaseHQ.Statistician.Database;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ChaseHQ.Statistician.StatisticianPlugin;
import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.DataValues.DataValues_Config;

public class Database {
	private static Database _singletonDB = null;

	private Connection connection = null;

	public Database() throws ClassNotFoundException, DBConnectFail {
		if (Database._singletonDB != null) return;

		// Connect To DB And Hold info
		Class.forName("com.mysql.jdbc.Driver");
		this.ConnectToDB();
		this.patchDB();

		Database._singletonDB = this;

		DataValues_Config.refresh();
	}

	private void ConnectToDB() throws DBConnectFail {
		try {
			this.connection = DriverManager.getConnection("jdbc:mysql://" + Config.getConfig().getDBAddress() + ":" + Config.getConfig().getDBPort() + "/" + Config.getConfig().getDBName(), Config.getConfig().getDBUsername(), Config.getConfig().getDBPassword());
		} catch (SQLException e) {
			throw new DBConnectFail(e);
		}
	}

	public boolean executeSynchUpdate(String sql) {
		int rowsChanged = 0;

		Statement statement = null;
		try {
			statement = this.connection.createStatement();
			rowsChanged = statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			StatisticianPlugin.getInstance().getLogger().warning(sql + " :: Update failed, checking connection... (" + e.getMessage() + ")");
			this.checkConnectionTryReconnect();
			return false;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {}
			}
		}
		return rowsChanged > 0;
	}

	public List<Map<String, String>> executeSynchQuery(String sql) {
		List<Map<String, String>> ColData = new ArrayList<Map<String, String>>();

		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = this.connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				HashMap<String, String> rowToAdd = new HashMap<String, String>();
				for (int x = 1; x <= rs.getMetaData().getColumnCount(); ++x) {
					rowToAdd.put(rs.getMetaData().getColumnName(x), rs.getString(x));
				}
				ColData.add(rowToAdd);
			}
		} catch (SQLException e) {
			StatisticianPlugin.getInstance().getLogger().warning(sql + " :: Query failed, checking connection... (" + e.getMessage() + ")");
			this.checkConnectionTryReconnect();
			return null;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {}
			}
		}
		return ColData;
	}

	public boolean callStoredProcedure(String procName, List<String> variables) {
		StringBuilder sb = new StringBuilder("CALL " + Config.getConfig().getDBName() + "." + procName + "(");
		if (variables != null) {
			for (String variable : variables) {
				sb.append("'" + variable + "',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		sb.append(");");

		Statement statement = null;
		try {
			statement = this.connection.createStatement();
			statement.executeUpdate(sb.toString());
		} catch (SQLException e) {
			StatisticianPlugin.getInstance().getLogger().warning(sb.toString() + " :: Stored procedure failed, checking connection... (" + e.getMessage() + ")");
			this.checkConnectionTryReconnect();
			return false;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {}
			}
		}

		return true;
	}

	private void checkConnectionTryReconnect() {
		try {
			if (this.connection.isValid(10)) {
				StatisticianPlugin.getInstance().getLogger().info("Connection is still present, it may of been a malformed query.");
			} else {
				this.reconnect();
			}
		} catch (SQLException e) {}
	}

	private void reconnect() {
		StatisticianPlugin.getInstance().getLogger().warning("Connection has been lost with database, attempting to reconnect.");
		try {
			this.ConnectToDB();
			StatisticianPlugin.getInstance().getLogger().info("Connection to the database re-established, some stats were lost though");
		} catch (DBConnectFail e) {
			StatisticianPlugin.getInstance().getLogger().severe("Could not reconnect, stats are going to be lost");
		}
	}

	private void patchDB() throws DBConnectFail {
		int version = 0;
		try {
			ResultSet rs = this.connection.createStatement().executeQuery("SELECT dbVersion FROM config");
			rs.next();
			version = rs.getInt(1);
		} catch (SQLException e) {
			StatisticianPlugin.getInstance().getLogger().info("Could not find a database version, creating one from scratch.");
			version = 0;
		}

		if (version < Config.getDBVersion()) {
			StatisticianPlugin.getInstance().getLogger().info("Patching database from v" + version + " to v" + Config.getDBVersion() + ".");

			while (version < Config.getDBVersion()) {
				++version;

				InputStream is = this.getClass().getClassLoader().getResourceAsStream("SQLPatches/stats_v" + version + ".sql");
				if (is == null) throw new DBConnectFail("Could not load database patch v" + version + ".");

				ScriptRunner sr = new ScriptRunner(this.connection);
				sr.setLogWriter(null);
				sr.setErrorLogWriter(null);
				sr.setAutoCommit(false);
				sr.setStopOnError(true);
				sr.setSendFullScript(false);
				sr.setRemoveCRs(true);
				try {
					sr.runScript(new InputStreamReader(is));
				} catch (RuntimeSqlException e) {
					throw new DBConnectFail("An error occured while executing the database patch v" + version + ".", e);
				}
			}

			StatisticianPlugin.getInstance().getLogger().info("Database patched to version " + version + ".");
		}
	}
}
