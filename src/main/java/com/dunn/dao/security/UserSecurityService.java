package com.dunn.dao.security;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;

@Service
@Transactional
public class UserSecurityService {

    @Autowired
    private IUserSecurityDAO userSecurityDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createPasswordResetTokenForUser(WoodulikeUser woodulikeUser, String token){
        PasswordResetToken passwordResetToken = new PasswordResetToken(woodulikeUser, token);
        userSecurityDAO.savePasswordResetToken(passwordResetToken);
    }

    public String validatePasswordResetToken(Long id, String token){
        final PasswordResetToken passwordResetToken = userSecurityDAO.findByToken(token);

        if(passwordResetToken == null || !passwordResetToken.getWoodulikeUser().getId().equals(id)){
            return "invalidToken";
        }

        if(LocalDate.now().isAfter(passwordResetToken.getExpiryDate())){
            return "expired";
        };

        final WoodulikeUser user = passwordResetToken.getWoodulikeUser();
        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(new UserRole("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        return null;
    }

    public void changePassword(WoodulikeUser woodulikeUser, String password){
        woodulikeUser.setPassword(passwordEncoder.encode(password));

    }

}
