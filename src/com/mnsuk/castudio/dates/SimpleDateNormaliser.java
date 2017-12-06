package com.mnsuk.castudio.dates;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.ibm.talent.jfst.norm.AbstractStringNormalizer;
import com.ibm.talent.jfst.norm.JFSTNormalizerException;

public class SimpleDateNormaliser extends AbstractStringNormalizer {
    private static final String defaultOutputDateFormat = "dd-MM-yyyy";
    private static final String inputDateFormat = "yyyyMMdd";
    private static final String defaultInputDate = "19700101";
	@Override
	public String normalize(String arg0, String[] arg1) throws JFSTNormalizerException {
		String dateFormat = arg1.length == 4 ? arg1[3] : defaultOutputDateFormat;
		String day = arg1[2].length() == 2 ? arg1[2] : twoDigit(arg1[2]);
		String month = arg1[1].length() == 2 ? arg1[1] : twoDigit(arg1[1]);
		String year = arg1[0].length() == 4 ? arg1[0] : fourDigitYear(arg1[0]);
		// accept 3 or 4 arguments (upper limit imposed by plugin)
		String inputDate = arg1.length >= 3 ? year+month+day : defaultInputDate;
		DateTimeFormatter outputFormatter, inputFormatter;
		try {
			inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
			outputFormatter = DateTimeFormatter.ofPattern(dateFormat);
		} catch (IllegalArgumentException e) {
			inputFormatter = DateTimeFormatter.ofPattern(inputDateFormat);
			outputFormatter = DateTimeFormatter.ofPattern(defaultOutputDateFormat);
		}
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(inputDate, inputFormatter);
		} catch (DateTimeParseException e) {
			parsedDate= LocalDate.parse(defaultInputDate, inputFormatter);
		}
		return parsedDate.format(outputFormatter);
	}
	
	/**
	 * Ensure that days and months are two digits with leading zero where necessary
	 * @param element  - day or month
	 * @return two digit day or month with leading zero if needed.
	 */
	private String twoDigit(String element) {
		String answer = "01";
		try {
			answer = String.format("%02d", Integer.parseInt(element));
		} catch (Exception e) {
			answer = "01";
		}
		return answer;
	}

	/**
	 * Convert 2 digit years to 4 digit where necessary assuming that the four-digit year 
	 * date should be in either the next 30 years, or the preceding 70 years
	 * @param inputYear - 2 digit year
	 * @return 4 digit year
	 */
	private String fourDigitYear(String inputYearStr) {
		String answer="1970";
		String thisYearStr;
		int thisYear, inputYear;
		if (inputYearStr.length() != 2)
			return answer;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
			thisYearStr = LocalDateTime.now().format(formatter);
			thisYear = Integer.parseInt(thisYearStr);
			inputYear = Integer.parseInt(inputYearStr);
			if ((thisYear + 30) >= inputYear)
				answer = "20" + inputYearStr;
			else
				answer = "19" + inputYearStr;			
		} catch (Exception e) {
			thisYearStr = answer;
		}	
		return answer;
	}	
}
