package utils;
/**
 * Utility methods.
 * @author berend
 *
 */
public class Utils {
	
	/**
	 * 
	 * @param codering
	 * @return
	 */
	public static int getCoderingOnder(double codering) {
		int coderingOnder = 0;
		if (codering == 0.0D) {
			coderingOnder = 0;
		} else if (codering == 1.0D) {
			coderingOnder = 0;
		} else if (codering == 2.0D) {
			coderingOnder = 1;
		} else if (codering == 3.0D) {
			coderingOnder = 1;
		}

		return coderingOnder;
	}

	public static int getCoderingBoven(double codering) {
		int coderingBoven = 0;
		if (codering == 0.0D) {
			coderingBoven = 0;
		} else if (codering == 1.0D) {
			coderingBoven = 0;
		} else if (codering == 2.0D) {
			coderingBoven = 1;
		} else if (codering == 3.0D) {
			coderingBoven = 1;
		}

		return coderingBoven;
	}
}
