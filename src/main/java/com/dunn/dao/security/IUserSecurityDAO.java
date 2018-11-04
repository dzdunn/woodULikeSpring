package com.dunn.dao.security;

import com.dunn.model.user.PasswordResetToken;
import com.dunn.model.user.WoodulikeUser;

import javax.persistence.criteria.CriteriaBuilder;

public interface IUserSecurityDAO {

    public void savePasswordResetToken(PasswordResetToken passwordResetToken);

    public void deletePasswordResetTokensForUser(Long userId);

    public PasswordResetToken findByToken(String token);

    public void changeUserPassword(WoodulikeUser woodulikeUser, String password);
}
