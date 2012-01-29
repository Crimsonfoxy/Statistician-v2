package com.ChaseHQ.Statistician.Database;

public class DBConnectFail extends Exception {
	private static final long serialVersionUID = 1325478012737090439L;

	@Override
	public String toString() {
		return "Operation Failed On DB";
	}
}
