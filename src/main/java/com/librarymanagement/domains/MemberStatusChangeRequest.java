package com.librarymanagement.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.librarymanagement.enums.MembershipStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Neerajkumar
 *
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MemberStatusChangeRequest {

    private MembershipStatus newStatus;

}
