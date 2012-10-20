package no.anderska.minesolver;

import no.anderska.minesolver.MineSolverApi.OpenResult;

public class MineSolverExample {

	public static void main(String[] args) {
		MineSolverApi mineSolverApi = new MineSolverApi();
		OpenResult hint = mineSolverApi.hint();
		printResult(hint);
		
		printResult(mineSolverApi.open(0, 0));

	}

	private static void printResult(OpenResult result) {
		System.out.println("X=" + result.getX() + ",Y=" + result.getY() + ",Count=" + result.getCount());
	}

}
