package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.AccountDTO;
import com.myproject.onlinecourses.dto.AccountDetailDTO;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AccountConvert {
    @Mapping(target = "role", source = "account.role.id")
    AccountDTO entityToAccountDTO(Account account);


    @Mapping(target = "role", ignore = true)
    Account accountDtoToEntity(AccountDTO dto);

    @Mappings({
            @Mapping(target = "username", source = "dto.username"),
            @Mapping(target = "password", source = "dto.password"),
            @Mapping(target ="role", ignore = true)
    })
    Account accountDetailToAccount(AccountDetailDTO dto);

    @Mappings({
            @Mapping(target = "username", source = "dto.username"),
            @Mapping(target = "fullname", source = "dto.fullname"),
            @Mapping(target = "birthdate", source = "dto.birthdate"),
            @Mapping(target = "gender", source = "dto.gender"),
            @Mapping(target = "email", source = "dto.email"),
            @Mapping(target = "phone", source = "dto.phone")
    })
    UserDetail accountDetailToUserDetail(AccountDetailDTO dto);

    @Mappings({
            @Mapping(target = "username", source = "account.username"),
            @Mapping(target = "password", source = "account.password"),
            @Mapping(target = "role", source = "account.role.name"),
            @Mapping(target = "fullname", source = "user.fullname"),
            @Mapping(target = "birthdate", source = "user.birthdate"),
            @Mapping(target = "gender", source = "user.gender"),
            @Mapping(target = "email", source = "user.email"),
            @Mapping(target = "phone", source = "user.phone")
    })
    AccountDetailDTO mergerAccountDetail(Account account, UserDetail user);

    @Mappings({
            @Mapping(target = "role", source =  "account.role.id"),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "fullname", source = "account.userDetail.fullname"),
            @Mapping(target = "birthdate", source = "account.userDetail.birthdate"),
            @Mapping(target = "gender", source = "account.userDetail.gender"),
            @Mapping(target = "email", source = "account.userDetail.email"),
            @Mapping(target = "phone", source = "account.userDetail.phone")
    }
    )
    AccountDetailDTO accountToDetail(Account account);

}
