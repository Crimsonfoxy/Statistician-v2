package com.ChaseHQ.Statistician.Database.DataValues;

import java.util.List;
import java.util.Map;

import com.ChaseHQ.Statistician.Config.Config;
import com.ChaseHQ.Statistician.Database.DBSynchDataGetSet;

public enum DataValues_Config implements IDataValues {
	DATABASE_VERSION("dbVersion"),
	SHOW_FIRSTJOIN_WELCOME("show_firstjoin_welcome"),
	FIRSTJOIN_WELCOME_MSG("firstjoin_welcome_msg"),
	SHOW_LASTJOIN_WELCOME("show_lastjoin_welcome"),
	LASTJOIN_WELCOME_MSG("lastjoin_welcome_message"),
	DATE_FORMAT("date_format");

	private final String columnName;
	private String configValue;

	private DataValues_Config(String ColName) {
		this.columnName = ColName;
	}

	@Override
	public String getColumnName() {
		return this.columnName;
	}

	public String getConfigValueString() {
		return this.configValue;
	}

	public Boolean getConfigValueBoolean() {
		if (this.configValue.equalsIgnoreCase("Y"))
			return true;
		return false;
	}

	public Integer getConfigValueInteger() {
		try {
			return Integer.parseInt(this.configValue);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	public void __internalSetConfigVal(String value) {
		this.configValue = value;
	}

	public static void refreshConfigValues() {
		List<Map<String, String>> results = DBSynchDataGetSet.getValues(DataStores.CONFIGURATION, DataValues_Config.DATABASE_VERSION, Integer.toString(Config.getDBVersion()));
		for (int x = 0; x < DataValues_Config.values().length; ++x) {
			DataValues_Config.values()[x].__internalSetConfigVal(results.get(0).get(DataValues_Config.values()[x].columnName));
		}
	}

	@Override
	public DataStores belongsToStore() {
		return DataStores.CONFIGURATION;
	}
}
