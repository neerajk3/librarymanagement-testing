/**
 * 
 */
package com.librarymanagement.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.librarymanagement.domains.MemberInfo;
import com.librarymanagement.entities.Member;
import com.librarymanagement.enums.MemberRole;
import com.librarymanagement.enums.MembershipStatus;
import com.librarymanagement.exceptions.Authorizationexception;
import com.librarymanagement.exceptions.BadRequestException;
import com.librarymanagement.exceptions.ForbiddenException;
import com.librarymanagement.repositories.MemberRepository;
import com.librarymanagement.utils.LibraryMgmtUtil;

/**
 * @author Neerajkumar
 *
 */
@Service
public class MemberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MemberRepository memberDaoMongoImpl;

    @Autowired
    private LibraryMgmtUtil libraryMgmtUtil;

    public MemberInfo createMember(String tokenString, MemberInfo memberInfo) throws Authorizationexception, ForbiddenException {

        verifyToken(tokenString, Arrays.asList(MemberRole.ADMIN));

        // converting MemberInfo to Member
        Member member = libraryMgmtUtil.getMember(memberInfo);

        // saving Member into Database
        Member persistedMemberDetail = memberDaoMongoImpl.save(member);

        LOGGER.info("member details persisted successfully, new member id:{}", persistedMemberDetail.getMemberId());

        // converting Member to MemberInfo
        MemberInfo createMemberResponse = libraryMgmtUtil.getMemberInfo(persistedMemberDetail);

        return createMemberResponse;
    }

    public void changeMemberstatus(String tokenString, String memberId, MembershipStatus status)
            throws Authorizationexception, ForbiddenException, BadRequestException {
        Member loggedInMember = verifyToken(tokenString, Arrays.asList(MemberRole.ADMIN, MemberRole.BORROWER));

        Member member;

        LOGGER.info("Logged in1:{}", loggedInMember.getMemberId().toString());
        LOGGER.info("Logged in2:{}", loggedInMember.getMemberId());
        if (!StringUtils.equals(loggedInMember.getMemberId().toString(), memberId)) {
            if (loggedInMember.getRole() != MemberRole.ADMIN) {
                LOGGER.error(
                        "logged in member doesn't have sufficient permission to execute change request, loggedin member:{},member id:{}",
                        loggedInMember.getMemberId(), memberId);
                throw new ForbiddenException("INSUFFICIENT ACCESS", "insufficient access to change status");
            }
            // Librarian trying to update member status
            // Getting Member Details
            member = memberDaoMongoImpl.findById(memberId).orElse(null);
            if (null == member) {
                LOGGER.error("member doesn't exist with member id:{}", memberId);
                throw new BadRequestException("INVALID MEMBER ID", "member details doesn't exist");
            }
        }
        else {
            member = loggedInMember;
            if (member.getRole() != MemberRole.BORROWER || status != MembershipStatus.CANCELED) {
                LOGGER.error("being as a member you don't have sufficient access to change status, member id:{}", memberId);
                throw new ForbiddenException("INSUFFICIENT ACCESS", "being as a member you don't have sufficient access to change status");
            }
        }

        if (status == MembershipStatus.ACTIVE) {
            member.setMembershipStartTime(LocalDateTime.now());
            member.setMembershipEndTime(LocalDateTime.now().plusYears(1));
        }
        else {
            member.setMembershipEndTime(LocalDateTime.now());
        }
        member.setStatus(status);
        memberDaoMongoImpl.save(member);

        LOGGER.info("change request processed successfully for member id:{}", member.getMemberId());
    }

    protected Member verifyToken(String tokenString, List<MemberRole> roles) throws Authorizationexception, ForbiddenException {
        LOGGER.trace("decoding and verifying token:{}", tokenString);
        String[] tokenArray = libraryMgmtUtil.decodeToken(tokenString);
        LOGGER.trace("decoded token:{}", Arrays.toString(tokenArray));
        if (tokenArray.length != 2) {
            LOGGER.error("given token is invalid");
            throw new Authorizationexception("INVALID TOKEN", "invalid is token");
        }
        Member member = memberDaoMongoImpl.findById(tokenArray[0]).orElse(null);
        if (null == member || !StringUtils.equals(new String(member.getPassword()), tokenArray[1])) {
            LOGGER.error("invalid member id password for member id:{}", tokenArray[0]);
            throw new Authorizationexception("INVALID CREDENTIAL", "invalid member id password");
        }

        if (!roles.contains(member.getRole())) {
            LOGGER.error("logged in member doesn't have sufficient permission to execute request");
            throw new ForbiddenException("INSUFFICIENT ACCESS", "loggedin user has insufficient access");
        }

        return member;
    }

}
