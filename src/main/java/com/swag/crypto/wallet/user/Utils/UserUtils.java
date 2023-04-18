package com.swag.crypto.wallet.user.Utils;

import com.swag.crypto.wallet.user.dto.UserDTO;
import com.swag.crypto.wallet.user.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtils {
    public static UserDTO mapToUserDto(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
        userDto.setHasAccount(user.getWallet() != null ? true : false);
        return userDto;
    }

    public static User mapDtoToUser(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setFullName(userDto.getFirstName().concat(" ").concat(userDto.getLastName()));
        user.setEmail(userDto.getEmail());
        return user;
    }

  public static List<String> extractMnemonichPhrase(String words){
        if(words != null) return Arrays.asList(words.trim().split(" "));
        else return null;

  }

}
