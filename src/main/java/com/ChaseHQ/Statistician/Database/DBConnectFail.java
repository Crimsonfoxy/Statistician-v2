package com.ChaseHQ.Statistician.Database;

public class DBConnectFail extends Exception {
	private static final long serialVersionUID = 1325478012737090439L;

	public DBConnectFail() {

	}

	public DBConnectFail(String message) {
		super(message);
	}

	public DBConnectFail(String message, Throwable cause) {
		super(message, cause);
	}

	public DBConnectFail(Throwable cause) {
		super(cause);
	}

	@Override
	public String toString() {
		String message = this.getLocalizedMessage();
		return message != null ? message : "Operation Failed On DB";
	}
}
