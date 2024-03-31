package com.elmar.loanguru.exception;

import com.elmar.loanguru.error.ErrorResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.io.Serial;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class LoanException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 1L;

	@Getter
	private final ErrorResponse errorResponse;
	private final Map<String, Object> messageArguments;

	public LoanException(ErrorResponse errorResponse, Map<String, Object> messageArguments) {
		this.errorResponse = errorResponse;
		this.messageArguments = messageArguments;
	}

	@Override
	public String getMessage() {
		return messageArguments.isEmpty() ? errorResponse.getMessage() :
				StringSubstitutor.replace(errorResponse.getMessage(), messageArguments, "{", "}");
	}

	public String getLocalizedMessage(Locale locale, MessageSource messageSource) {
		try {
			String localizedMessage = messageSource.getMessage(errorResponse.getKey(), new Object[]{}, locale);
			return messageArguments.isEmpty() ? localizedMessage :
					StringSubstitutor.replace(localizedMessage, messageArguments, "{", "}");
		} catch (NoSuchMessageException exception) {
			log.warn("Please consider adding localized message for key {} and locale {}",
					errorResponse.getKey(), locale);
		}
		return getMessage();
	}
}
