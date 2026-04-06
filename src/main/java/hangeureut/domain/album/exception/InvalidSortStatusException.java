package hangeureut.domain.album.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class InvalidSortStatusException extends GeneralException {
	public InvalidSortStatusException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
