/**
 * 
 */
package com.librarymanagement.domains;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.entities.Address;
import com.librarymanagement.enums.MembershipStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MemberInfo {

    private String memberId;

    private String password;

    @ApiModelProperty(name = "contactNumber", dataType = "String", example = "919191919991")
    private String contactNumber;

    @ApiModelProperty(name = "emailId", dataType = "String", example = "testmail@gmail.com")
    private String emailId;

    private Address address;

    private LocalDateTime membershipStartTime;

    private LocalDateTime membershipEndTime;

    private MembershipStatus status;

}
