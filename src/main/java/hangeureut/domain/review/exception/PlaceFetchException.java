package hangeureut.domain.review.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class PlaceFetchException extends GeneralException {
	public PlaceFetchException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
