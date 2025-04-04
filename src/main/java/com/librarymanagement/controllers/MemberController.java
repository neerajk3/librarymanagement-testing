/**
 * 
 */
package com.librarymanagement.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.librarymanagement.constants.LibraryMgmtContant;
import com.librarymanagement.domains.GenericResponse;
import com.librarymanagement.domains.MemberInfo;
import com.librarymanagement.domains.MemberStatusChangeRequest;
import com.librarymanagement.exceptions.Authorizationexception;
import com.librarymanagement.exceptions.BadRequestException;
import com.librarymanagement.exceptions.ForbiddenException;
import com.librarymanagement.services.MemberService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * * {@link MemberController} contains action that can be performed by Librarian
 * and Member
 * 
 * Librarian can perform - Create New Member , Cancel Member , Block Member and
 * UnBlock Member
 * 
 * Member can perform only cancel member
 * 
 * @author Neerajkumar
 *
 */
@Api(tags = "Member Apis", description = "APIs for member related operation")
@RestController
public class MemberController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "Add or Create Member")
    @PostMapping(value = "/v1/members", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk")
    public ResponseEntity<MemberInfo> createMember(@RequestHeader("Authorization") String token, @RequestBody MemberInfo memberInfo)
            throws Authorizationexception, ForbiddenException {

        LOGGER.debug("processing create member request");

        MemberInfo response = memberService.createMember(token, memberInfo);

        return new ResponseEntity<MemberInfo>(response, HttpStatus.OK);

    }

    @ApiOperation(value = "Change member status")
    @PostMapping(value = "/v1/members/{memberId}/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "NTA3ZjE5MWU4MTBjMTk3MjlkZTg2MGVhOnBhc3N3b3Jk"),
            @ApiImplicitParam(name = "memberId", value = "memberId", required = true, allowEmptyValue = false, paramType = "path", dataTypeClass = String.class, example = "507f191e810c19729de860eb")
    })
    public ResponseEntity<GenericResponse> changeMemberstatus(@RequestHeader("Authorization") String token,
            @PathVariable("memberId") String memberId, @RequestBody MemberStatusChangeRequest changeRequest)
            throws Authorizationexception, ForbiddenException, BadRequestException {
        LOGGER.debug("processing change status request for member id :{}", memberId);
        memberService.changeMemberstatus(token, memberId, changeRequest.getNewStatus());
        return new ResponseEntity<GenericResponse>(new GenericResponse(LibraryMgmtContant.STATUS_CHANGE_RESPONSE_SUCCESS_MESSSGE),
                HttpStatus.OK);

    }

}
