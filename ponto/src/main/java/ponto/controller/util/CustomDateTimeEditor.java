package ponto.controller.util;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class CustomDateTimeEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) {
		if (StringUtils.isBlank(text)) {
			setValue(null);
			return;
		}
		try {
			Pattern pattern = Pattern.compile("^[0-9]{2}/[0-9]{4}$");
			Matcher matcher = pattern.matcher(text);
			String formato = "dd/MM/yyyy";
			if (matcher.matches()) {
				formato = "MM/yyyy";
			}
			setValue(DateTime.parse(text, DateTimeFormat.forPattern(formato)));
		} catch (IllegalArgumentException e) {
			setValue(null);
		} catch (Exception e) {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return null;
		} else {
			DateTime value = (DateTime) getValue();
			return value.toString("dd/MM/yyyy");
		}
	}

}
