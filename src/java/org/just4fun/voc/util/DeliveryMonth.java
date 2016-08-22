package org.just4fun.voc.util;

// https://en.wikipedia.org/wiki/Delivery_month
// http://www.cmegroup.com/month-codes.html
public class DeliveryMonth {
	
	public enum Month {
		January,
		February,
		March,
		April,
		May,
		June,
		July,
		August,
		September,
		October,
		November,
		December
	}
	
	public static String getCode(Month month) {
		switch (month) {
		case January:
			return "F";
		case February:
		    return "G";
		case March:
			return "H";
		case April:
			return "J";
		case May:
			return "K";
		case June:
			return "M";
		case July:
			return "N";
		case August:
			return "Q";
		case September:
			return "U";
		case October:
			return "V";
		case November:
			return "X";
		case December:
			return "Z";
		}
		return "UNKNOWN";
	}

	public static void main(String[] args) {
		System.out.println(getCode(Month.October));
	}

}
