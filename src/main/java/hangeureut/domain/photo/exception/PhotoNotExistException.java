package hangeureut.domain.photo.exception;

import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

public class PhotoNotExistException extends GeneralException {
	public PhotoNotExistException() {
		super(ErrorStatus._PHOTO_NOT_EXIST);
	}
}
