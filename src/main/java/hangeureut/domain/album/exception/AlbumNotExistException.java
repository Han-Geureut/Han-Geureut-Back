package hangeureut.domain.album.exception;

import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

public class AlbumNotExistException extends GeneralException {
	public AlbumNotExistException() {
		super(ErrorStatus._ALBUM_NOT_EXIST);
	}
}
