package hangeureut.domain.user.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class UserNotExistException extends GeneralException {
	public UserNotExistException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
