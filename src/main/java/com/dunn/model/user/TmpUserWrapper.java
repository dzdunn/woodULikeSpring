package com.dunn.model.user;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public class TmpUserWrapper extends AbstractAuthenticationToken {

    private WoodulikeUser woodulikeUser;
    private Object credentials;
    private Object principal;

    public TmpUserWrapper(Object credentials, Collection<? extends GrantedAuthority> authorities, WoodulikeUser woodulikeUser){
        super(authorities);
        this.principal = "TmpUser:" + UUID.randomUUID().toString();
        this.credentials = credentials;
        this.woodulikeUser = woodulikeUser;
    }

    public TmpUserWrapper(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, WoodulikeUser woodulikeUser){
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.woodulikeUser = woodulikeUser;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public WoodulikeUser getWoodulikeUser(){
        return woodulikeUser;
    }

}
