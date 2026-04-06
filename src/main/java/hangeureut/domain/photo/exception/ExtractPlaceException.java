package hangeureut.domain.photo.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class ExtractPlaceException extends GeneralException {
	public ExtractPlaceException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
