package com.myexample.demofullstack.service;

import com.myexample.demofullstack.dto.EditUserRequestDto;
import com.myexample.demofullstack.exception.UserAlreadyExistException;
import com.myexample.demofullstack.model.AppUser;
import com.myexample.demofullstack.repository.AppUserRepository;
import com.myexample.demofullstack.s3.S3FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.myexample.demofullstack.model.UserRole.ADMIN;
import static com.myexample.demofullstack.model.UserRole.USER;

@Service
public class UserService {

    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private S3FileStore fileStore;
    @Autowired
    private Environment env;

    public AppUser createUser(AppUser user) {
        try {
            user.setRole(USER.getRole());
            user.setPassword(encoder.encode(user.getRawPassword()));
            user.setEnabled(true);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistException(String.format("Username %s already exists", user.getUsername()));
        }
    }

    public AppUser updateUser(EditUserRequestDto editUserRequestDto, String username) {
        AppUser toChange = getUser(username);
        toChange.setEmail(editUserRequestDto.getEmail());
        toChange.setPhone(editUserRequestDto.getPhone());
        // user.setEncodedPassword(encoder.encode(user.getPassword()));
        return userRepository.save(toChange);
    }

    public String deactivateAccount(String username) {
        AppUser deactivateUser = getUser(username);
        deactivateUser.setEnabled(false);
        deactivateUser.getOwnedProduct().forEach(p -> p.setOwnerActive(false));
        userRepository.save(deactivateUser);
        return String.format("Deactivated user: %s", username);
    }

    public AppUser getUser(String username) {
        return userRepository.findFirstByUsernameAndEnabled(username, true)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User not found: %s", username)));
    }

    public AppUser createAdmin(AppUser user) {
        user.setRole(ADMIN.getRole());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public AppUser addProfilePicture(String username, MultipartFile file){
        AppUser user = getUser(username);
        uploadUserProfileImage(user, file);
        return userRepository.save(user);
    }


    private void uploadUserProfileImage(AppUser user, MultipartFile file) {
        Map<String, String> metadata = getFileMetadata(file);

        String path = String.format("%s/%s/profile", env.getProperty("S3BucketName"), user.getUsername());
        String fileName = file.getOriginalFilename();
        String savedPath = String.format("%s/profile/%s", user.getUsername(), fileName);
        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            user.setProfilePictureUrl(String.format("https://%s.s3-ap-southeast-1.amazonaws.com/%s", env.getProperty("S3BucketName"), savedPath));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }



//    public byte[] downloadUserProfileImage(UUID userId) {
//        AppUser user = getUserProfileOrCheckExist(userId);
//        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
//        return user.getProfileImageLink().map(link -> fileStore.download(path, link)).orElse(new byte[0]);
//    }

    private Map<String, String> getFileMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }


}


