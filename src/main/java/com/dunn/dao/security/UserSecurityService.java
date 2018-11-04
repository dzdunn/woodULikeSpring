package com.dunn.dao.security;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.UserRole;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.UUID;

@Service
@Transactional
public class UserSecurityService {

    @Autowired
    private IUserSecurityDAO userSecurityDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createPasswordResetTokenForUser(WoodulikeUser woodulikeUser, String token) {
        userSecurityDAO.deletePasswordResetTokensForUser(woodulikeUser.getId());
        PasswordResetToken passwordResetToken = new PasswordResetToken(woodulikeUser, token);
        userSecurityDAO.savePasswordResetToken(passwordResetToken);
    }

    public boolean validatePasswordResetToken(Long id, String token) {
        final PasswordResetToken passwordResetToken = userSecurityDAO.findByToken(token);

        if (passwordResetToken == null ||
                !passwordResetToken.getWoodulikeUser().getId().equals(id) ||
                LocalDate.now().isAfter(passwordResetToken.getExpiryDate())) {
            return false;
        }

        return true;
    }

    public void changePassword(WoodulikeUser woodulikeUser, String password) {
        userSecurityDAO.changeUserPassword(woodulikeUser, passwordEncoder.encode(password));

    }

    public PasswordResetToken findByToken(String token){
        return userSecurityDAO.findByToken(token);
    }

}
