package com.ph.shopping.common.util.date;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DoubleUtils {
	public static double add(double val1, double val2) {
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
		BigDecimal b2 = new BigDecimal(Double.toString(val2));
		return b1.add(b2).doubleValue();
	}

	public static double subtract(double val1, double val2) {
		BigDecimal b1 = new BigDecimal(Double.toString(val1));
		BigDecimal b2 = new BigDecimal(Double.toString(val2));
		return b1.subtract(b2).doubleValue();
	}

	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double val1, double val2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale 必须大于等于0");
		} else {
			BigDecimal b1 = new BigDecimal(Double.toString(val1));
			BigDecimal b2 = new BigDecimal(Double.toString(val2));
			return b1.divide(b2, scale, 4).doubleValue();
		}
	}

	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("scale必须大于等于0");
		} else {
			BigDecimal b = new BigDecimal(Double.toString(v));
			BigDecimal one = new BigDecimal("1");
			return b.divide(one, scale, 4).doubleValue();
		}
	}

	public static boolean equals(double num1, double num2) {
		return equals(num1, num2, (Integer) null);
	}

	public static boolean equals(double num1, double num2, Integer decimalNum) {
		BigDecimal data1 = new BigDecimal(num1);
		BigDecimal data2 = new BigDecimal(num2);
		if (decimalNum != null) {
			data1 = data1.setScale(decimalNum.intValue(), 4);
			data2 = data2.setScale(decimalNum.intValue(), 4);
		}

		return data1.compareTo(data2) == 0;
	}

	public static double scaleDouble(double num, int decimalNum) {
		return scaleDouble(num, decimalNum, 4);
	}

	public static double scaleDouble(double num, int decimalNum, int roundingMode) {
		BigDecimal data1 = new BigDecimal(num);
		data1 = data1.setScale(decimalNum + 1, 4);
		return data1.setScale(decimalNum, roundingMode).doubleValue();
	}

	public static double tworound(double d) {
		DecimalFormat pcpricedfc = new DecimalFormat("##.00");
		return Double.parseDouble(pcpricedfc.format(d));
	}

	public static String formatRound(double d, int scale) {
		BigDecimal b = new BigDecimal(d);
		double f1 = b.setScale(5, 4).doubleValue();
		String fom = "0.0";
		if (scale == 2) {
			fom = "0.00";
		} else if (scale == 3) {
			fom = "0.000";
		} else if (scale == 4) {
			fom = "0.0000";
		} else if (scale == 5) {
			fom = "0.00000";
		}

		DecimalFormat decimalFormat = new DecimalFormat(fom);
		return decimalFormat.format(f1);
	}
}