/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package account;

import java.io.Serializable;

/**
 *
 * @author truongtn
 */
public class AccountCreateError implements Serializable {

    private String emailTypeErr;
    private String emailLengthErr;
    private String emailIsNotAvailability;
    private String passwordLengthErr;
    private String fullnameLengthErr;
    private String confirmNotMatched;
    private String emailIsExisted;

    public AccountCreateError() {
    }

    public String getEmailTypeErr() {
        return emailTypeErr;
    }

    public void setEmailTypeErr(String emailTypeErr) {
        this.emailTypeErr = emailTypeErr;
    }

    public String getEmailLengthErr() {
        return emailLengthErr;
    }

    public void setEmailLengthErr(String emailLengthErr) {
        this.emailLengthErr = emailLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getFullnameLengthErr() {
        return fullnameLengthErr;
    }

    public void setFullnameLengthErr(String fullnameLengthErr) {
        this.fullnameLengthErr = fullnameLengthErr;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

    public String getEmailIsNotAvailability() {
        return emailIsNotAvailability;
    }

    public void setEmailIsNotAvailability(String emailIsNotAvailability) {
        this.emailIsNotAvailability = emailIsNotAvailability;
    }

}
