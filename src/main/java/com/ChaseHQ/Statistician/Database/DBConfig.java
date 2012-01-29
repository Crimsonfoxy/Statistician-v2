package com.ChaseHQ.Statistician.Database;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ChaseHQ.Statistician.Log;
import com.ChaseHQ.Statistician.Config.Config;

public class DBConfig {
	public DBConfig(Connection connection) throws DBConnectFail {
		// Load the config
		this.LoadConfigSetupDB(connection);
	}

	private void LoadConfigSetupDB(Connection connection) throws DBConnectFail {
		int curDBVersion = 0;
		if ((curDBVersion = this.getDBVersion(connection)) != Config.getDBVersion()) {
			for (int x = curDBVersion; x < Config.getDBVersion(); x++) {
				this.patchDB(x + 1, connection);
			}
		}
	}

	private void patchDB(int versionPatch, Connection connection) throws DBConnectFail {
		ScriptRunner sr = new ScriptRunner(connection, false, true);
		String SQLPatchFile = new String();

		Log.ConsoleLog("Patching Database To Version " + versionPatch + ".");
		SQLPatchFile = "stats_v" + versionPatch + ".sql";

		InputStream is = this.getClass().getClassLoader().getResourceAsStream("SQLPatches/" + SQLPatchFile);
		if (is == null) {
			Log.ConsoleLog("Could Not Load Database Patch File. Is it removed from the Jar?!");
			throw new DBConnectFail();
		}
		try {
			sr.runScript(new InputStreamReader(is));
		} catch (IOException e) {
			Log.ConsoleLog("Critical Error, There is a problem with the internal SQL file.");
			throw new DBConnectFail();
		} catch (SQLException e) {
			Log.ConsoleLog("Critical Error, There was an error in executing the internal SQL file.");
		}
		Log.ConsoleLog("Database Patch Complete. DBVersion: " + versionPatch);
	}

	private int getDBVersion(Connection connection) {
		int dbVersion = 0;
		try {
			ResultSet rs = connection.createStatement().executeQuery("SELECT dbVersion FROM config");
			rs.next();
			dbVersion = rs.getInt(1);
		} catch (SQLException e) {
			Log.ConsoleLog("Could Not find a Database Version, Creating one from scratch.");
			return 0;
		}
		return dbVersion;
	}
}
