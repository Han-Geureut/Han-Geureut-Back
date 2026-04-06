package hangeureut.domain.photo.exception;

import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

public class MetadataNotExistException extends GeneralException {
	public MetadataNotExistException() {
		super(ErrorStatus._METADATA_NOT_EXIST);
	}
}
