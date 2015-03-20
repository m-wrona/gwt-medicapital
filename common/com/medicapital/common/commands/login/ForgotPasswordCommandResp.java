package com.medicapital.common.commands.login;

import com.medicapital.common.commands.CommandResp;
import com.medicapital.common.entities.LoginData;

public class ForgotPasswordCommandResp extends CommandResp<LoginData> {

    private boolean emailSent = false;

    /**
     * Constructor for RPC communication
     */
    protected ForgotPasswordCommandResp() {
        super();
    }

    public ForgotPasswordCommandResp(boolean emailSent) {
        super(LoginData.class);
        this.emailSent = emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    @Override
    public String toString() {
        return "[" + getClass() + ", emailSent=" + emailSent + "]";
    }
}
