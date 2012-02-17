package com.ChaseHQ.Statistician.Database;

/* http://code.google.com/p/mybatis/source/browse/trunk/src/main/java/org/apache/ibatis/jdbc/ScriptRunner.java
 * r4535 - Jan 5, 2012
 */
/* Added the ability to change the delimiter so you can run scripts that 
 * contain stored procedures.
 * - ChaseHQ
 */
/*
 *    Copyright 2009-2011 The MyBatis Team
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {

	private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");

	private static final String DEFAULT_DELIMITER = ";";

	private Connection connection;

	private boolean stopOnError;
	private boolean autoCommit;
	private boolean sendFullScript;
	private boolean removeCRs;

	private PrintWriter logWriter = new PrintWriter(System.out);
	private PrintWriter errorLogWriter = new PrintWriter(System.err);

	private String delimiter = ScriptRunner.DEFAULT_DELIMITER;
	private boolean fullLineDelimiter = false;

	public ScriptRunner(Connection connection) {
		this.connection = connection;
	}

	public void setStopOnError(boolean stopOnError) {
		this.stopOnError = stopOnError;
	}

	public void setAutoCommit(boolean autoCommit) {
		this.autoCommit = autoCommit;
	}

	public void setSendFullScript(boolean sendFullScript) {
		this.sendFullScript = sendFullScript;
	}

	public void setRemoveCRs(boolean removeCRs) {
		this.removeCRs = removeCRs;
	}

	public void setLogWriter(PrintWriter logWriter) {
		this.logWriter = logWriter;
	}

	public void setErrorLogWriter(PrintWriter errorLogWriter) {
		this.errorLogWriter = errorLogWriter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void setFullLineDelimiter(boolean fullLineDelimiter) {
		this.fullLineDelimiter = fullLineDelimiter;
	}

	public void runScript(Reader reader) throws RuntimeSqlException {
		this.setAutoCommit();

		try {
			if (this.sendFullScript) {
				this.executeFullScript(reader);
			} else {
				this.executeLineByLine(reader);
			}
		} finally {
			this.rollbackConnection();
		}
	}

	private void executeFullScript(Reader reader) throws RuntimeSqlException {
		StringBuilder script = new StringBuilder();
		try {
			BufferedReader lineReader = new BufferedReader(reader);
			String line;
			while ((line = lineReader.readLine()) != null) {
				script.append(line);
				script.append(ScriptRunner.LINE_SEPARATOR);
			}
			this.executeStatement(script.toString());
			this.commitConnection();
		} catch (Exception e) {
			String message = "Error executing: " + script + ".  Cause: " + e;
			this.printlnError(message);
			throw new RuntimeSqlException(message, e);
		}
	}

	private void executeLineByLine(Reader reader) throws RuntimeSqlException {
		StringBuilder command = new StringBuilder();
		try {
			BufferedReader lineReader = new BufferedReader(reader);
			String line;
			while ((line = lineReader.readLine()) != null) {
				command = this.handleLine(command, line);
			}
			this.commitConnection();
			this.checkForMissingLineTerminator(command);
		} catch (Exception e) {
			String message = "Error executing: " + command + ".  Cause: " + e;
			this.printlnError(message);
			throw new RuntimeSqlException(message, e);
		}
	}

	public void closeConnection() {
		try {
			this.connection.close();
		} catch (Exception e) {
			// ignore
		}
	}

	private void setAutoCommit() throws RuntimeSqlException {
		try {
			if (this.autoCommit != this.connection.getAutoCommit()) {
				this.connection.setAutoCommit(this.autoCommit);
			}
		} catch (Throwable t) {
			throw new RuntimeSqlException("Could not set AutoCommit to " + this.autoCommit + ". Cause: " + t, t);
		}
	}

	private void commitConnection() throws RuntimeSqlException {
		try {
			if (!this.connection.getAutoCommit()) {
				this.connection.commit();
			}
		} catch (Throwable t) {
			throw new RuntimeSqlException("Could not commit transaction. Cause: " + t, t);
		}
	}

	private void rollbackConnection() {
		try {
			if (!this.connection.getAutoCommit()) {
				this.connection.rollback();
			}
		} catch (Throwable t) {
			// ignore
		}
	}

	private void checkForMissingLineTerminator(StringBuilder command) throws RuntimeSqlException {
		if (command != null && command.toString().trim().length() > 0) throw new RuntimeSqlException("Line missing end-of-line terminator (" + this.delimiter + ") => " + command);
	}

	private StringBuilder handleLine(StringBuilder command, String line) throws SQLException, UnsupportedEncodingException {
		String trimmedLine = line.trim();
		if (trimmedLine.toLowerCase().startsWith("delimiter")) { // Support changing of the delimiter in the SQL.
			this.setDelimiter(trimmedLine.substring(10));
		} else if (this.lineIsComment(trimmedLine)) {
			this.println(trimmedLine);
		} else if (this.commandReadyToExecute(trimmedLine)) {
			command.append(line.substring(0, line.lastIndexOf(this.delimiter)));
			command.append(ScriptRunner.LINE_SEPARATOR);
			this.println(command);
			this.executeStatement(command.toString());
			command.setLength(0);
		} else if (trimmedLine.length() > 0) {
			command.append(line);
			command.append(ScriptRunner.LINE_SEPARATOR);
		}
		return command;
	}

	private boolean lineIsComment(String trimmedLine) {
		return trimmedLine.startsWith("//") || trimmedLine.startsWith("--");
	}

	private boolean commandReadyToExecute(String trimmedLine) {
		return !this.fullLineDelimiter && trimmedLine.endsWith(this.delimiter) || this.fullLineDelimiter && trimmedLine.equals(this.delimiter);
	}

	private void executeStatement(String command) throws SQLException, UnsupportedEncodingException {
		boolean hasResults = false;
		Statement statement = this.connection.createStatement();
		String sql = command;
		if (this.removeCRs) {
			sql = sql.replaceAll("\r\n", "\n");
		}
		if (this.stopOnError) {
			hasResults = statement.execute(sql);
		} else {
			try {
				hasResults = statement.execute(sql);
			} catch (SQLException e) {
				String message = "Error executing: " + command + ".  Cause: " + e;
				this.printlnError(message);
			}
		}
		this.printResults(statement, hasResults);
		try {
			statement.close();
		} catch (Exception e) {
			// Ignore to workaround a bug in some connection pools
		}
	}

	private void printResults(Statement statement, boolean hasResults) {
		try {
			if (hasResults) {
				ResultSet rs = statement.getResultSet();
				if (rs != null) {
					ResultSetMetaData md = rs.getMetaData();
					int cols = md.getColumnCount();
					for (int i = 0; i < cols; i++) {
						String name = md.getColumnLabel(i + 1);
						this.print(name + "\t");
					}
					this.println("");
					while (rs.next()) {
						for (int i = 0; i < cols; i++) {
							String value = rs.getString(i + 1);
							this.print(value + "\t");
						}
						this.println("");
					}
				}
			}
		} catch (SQLException e) {
			this.printlnError("Error printing results: " + e.getMessage());
		}
	}

	private void print(Object o) {
		if (this.logWriter != null) {
			this.logWriter.print(o);
			this.logWriter.flush();
		}
	}

	private void println(Object o) {
		if (this.logWriter != null) {
			this.logWriter.println(o);
			this.logWriter.flush();
		}
	}

	private void printlnError(Object o) {
		if (this.errorLogWriter != null) {
			this.errorLogWriter.println(o);
			this.errorLogWriter.flush();
		}
	}

}
