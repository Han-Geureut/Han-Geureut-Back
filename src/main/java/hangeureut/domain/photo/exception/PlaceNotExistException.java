package hangeureut.domain.photo.exception;

import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

public class PlaceNotExistException extends GeneralException {
	public PlaceNotExistException() {
		super(ErrorStatus._PLACE_NOT_EXIST);
	}
}
