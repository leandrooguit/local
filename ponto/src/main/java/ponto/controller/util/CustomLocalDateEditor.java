package ponto.controller.util;

import java.beans.PropertyEditorSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class CustomLocalDateEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
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
			setValue(LocalDate.parse(text, DateTimeFormat.forPattern(formato)));
		} catch (Exception e) {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return null;
		} else {
			LocalDate value = (LocalDate) getValue();
			return value.toString("dd/MM/yyyy");
		}
	}

}
