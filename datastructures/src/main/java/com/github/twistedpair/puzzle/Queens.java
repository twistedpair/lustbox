package com.github.twistedpair.puzzle;



public class Queens {

    public static boolean explore(final Board b, final int col) {
        if (col > b.size()) {
			return true;
		}
		else {
            for (int row = 1; row <= b.size(); row++) {
				if (b.safe(row, col)) {
                    b.place(row, col);
                    if (explore(b, col + 1)) {
						return true;
					}
                    b.remove(row, col);
                }
			}
            return false;
        }
    }
}
