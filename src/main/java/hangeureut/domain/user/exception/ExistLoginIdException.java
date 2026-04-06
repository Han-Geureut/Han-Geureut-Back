package hangeureut.domain.user.exception;

import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

public class ExistLoginIdException extends GeneralException {
	public ExistLoginIdException() {
		super(ErrorStatus._EXIST_LOGINID);
	}
}
