package hangeureut.domain.review.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class PageVariableInvalidException extends GeneralException {
	public PageVariableInvalidException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
