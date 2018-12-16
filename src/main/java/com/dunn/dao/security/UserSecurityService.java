package com.dunn.dao.security;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.WoodulikeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class UserSecurityService {

    @Autowired
    private IUserSecurityDAO userSecurityDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MessageSource messageSource;

    public void createPasswordResetTokenForUser(WoodulikeUser woodulikeUser, String token) {
        userSecurityDAO.deletePasswordResetTokensForUser(woodulikeUser.getId());
        PasswordResetToken passwordResetToken = new PasswordResetToken(woodulikeUser, token);
        userSecurityDAO.savePasswordResetToken(passwordResetToken);
    }

    public String validatePasswordResetToken(Long id, String token) {
        final PasswordResetToken passwordResetToken = userSecurityDAO.findByToken(token);

        if(passwordResetToken == null){
           return messageSource.getMessage("validation.passwordresettoken.tokenIsNull", null, LocaleContextHolder.getLocale());

        }
        if(!passwordResetToken.getWoodulikeUser().getId().equals(id)){
            return messageSource.getMessage("validation.passwordresettoken.woodulikeuser.notEmpty", null, LocaleContextHolder.getLocale());
        }
        if(LocalDate.now().isAfter(passwordResetToken.getExpiryDate())){
            return messageSource.getMessage("validation.passwordresettoken.expirydate.expired", null, LocaleContextHolder.getLocale());
        }
        return null;
    }

    public void changePassword(WoodulikeUser woodulikeUser, String password) {
        userSecurityDAO.changeUserPassword(woodulikeUser, passwordEncoder.encode(password));

    }

    public PasswordResetToken findByToken(String token){
        return userSecurityDAO.findByToken(token);
    }

}
