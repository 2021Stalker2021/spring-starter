package com.dmdev.spring.dto;

import com.dmdev.spring.database.entity.Role;
import com.dmdev.spring.validation.UserInfo;
import com.dmdev.spring.validation.group.UpdateAction;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@FieldNameConstants
@UserInfo(groups = UpdateAction.class)
public class UserCreateEditDto {

    @Email
    String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @NotBlank(message = "Firstname is required")
    @Size(min = 3, max = 64)
    String firstname;

    @NotBlank(message = "Firstname is required")
    String lastname;

    Role role;

    Integer companyId;

    MultipartFile image; // название должно соот.названию в view. Например в user/user (name="image")
}
