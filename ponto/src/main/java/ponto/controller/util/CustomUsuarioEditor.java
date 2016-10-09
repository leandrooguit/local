package ponto.controller.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

import ponto.model.domain.Usuario;

public class CustomUsuarioEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			setValue(null);
			return;
		}
		try {
			Usuario usuario = new Usuario();
			usuario.setId(Long.valueOf(text));
			setValue(usuario);
		} catch (Exception e) {
			setValue(null);
		}
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return null;
		} else {
			Usuario value = (Usuario) getValue();
			return String.valueOf(value.getId());
		}
	}

}
