package hangeureut.domain.user.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.user.converter.UserConverter;
import hangeureut.domain.user.entity.Authority;
import hangeureut.domain.user.entity.User;
import hangeureut.domain.user.entity.mapping.Follow;
import hangeureut.domain.user.enums.LoginType;
import hangeureut.domain.user.enums.Role;
import hangeureut.domain.user.exception.ExistLoginIdException;
import hangeureut.domain.user.exception.UserNotExistException;
import hangeureut.domain.user.repository.FollowRepository;
import hangeureut.domain.user.repository.UserRepository;
import hangeureut.domain.user.web.dto.UserRequestDTO;
import hangeureut.domain.user.web.dto.UserResponseDTO;
import hangeureut.global.aws.s3.AmazonS3Manager;
import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService {
	private static final Logger log = LogManager.getLogger(UserCommandServiceImpl.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AmazonS3Manager amazonS3Manager;

	private final FollowRepository followRepository;

	@Override
	@Transactional
	public User join(UserRequestDTO.SignupRequestDTO request) {
		if (userRepository.existsByLoginIdAndLoginType(request.getLoginId(), LoginType.LOCAL)) {
			log.error("중복되는 로그인 아이디가 있습니다.");
			throw new ExistLoginIdException();
		}

		String encodedPassword = passwordEncoder.encode(request.getPassword());
		User user = UserConverter.toUser(request, encodedPassword, LoginType.LOCAL);
		log.info("user = {}", user);
		user.addAuthority(Authority.builder().role(Role.ROLE_USER).build());
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public void addUserFollower(User followingUser, Long followerUserId) {
		User followerUser = userRepository.findById(followerUserId)
			.orElseThrow(() -> new UserNotExistException(ErrorStatus._USER_NOT_EXIST));

		// 만약 이미 팔로우 상태라면 return
		if (followRepository.existsByFollowerIdAndFollowingId(followerUser.getId(), followingUser.getId()))
			return;

		Follow follow = Follow.builder().build();
		followRepository.save(follow);
		followingUser.addFollowing(follow);
		followerUser.addFollower(follow);
	}

	@Override
	@Transactional
	public void removeUserFollower(User followingUser, Long followerUserId) {
		User followerUser = userRepository.findById(followerUserId)
			.orElseThrow(() -> new UserNotExistException(ErrorStatus._USER_NOT_EXIST));

		// 만약 팔로우 상태가 아니라면 return
		followRepository.findByFollowerIdAndFollowingId(followerUser.getId(), followingUser.getId())
			.ifPresent(follow -> {
				follow.getFollower().removeFollower(follow);
				follow.getFollowing().removeFollowing(follow);
				followRepository.delete(follow);
			});
	}

	@Override
	@Transactional
	public UserResponseDTO.UpdateUserProfileResponseDTO updateProfile(User user,
		UserRequestDTO.UpdateProfileRequestDTO request) {

		if (request.getUserName() != null && !request.getUserName().isBlank() && !request.getUserName()
			.equals(user.getUserName())) {
			if (userRepository.existsByUserName(request.getUserName())) {
				throw new GeneralException(ErrorStatus._EXIST_USERNAME);
			}
		}

		String savedUrl = null;

		if (request.getProfileImage() != null) {
			byte[] imageByte;
			try {
				imageByte = request.getProfileImage().getBytes();
			} catch (Exception e) {
				log.error("Multipartfile byte 추출 중 에러가 발생했습니다. {}", e);
				throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
			}
			savedUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateProfileImageKeyName(),
				request.getProfileImage(),
				imageByte);
		}

		user.updateProfile(request, savedUrl);

		return UserConverter.toUpdateUserProfileResponseDTO(userRepository.saveAndFlush(user));
	}

}
