package hangeureut.global.aws.exception;

import hangeureut.global.enums.statuscode.BaseCode;
import hangeureut.global.exception.GeneralException;

public class S3FileSaveException extends GeneralException {
	public S3FileSaveException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
