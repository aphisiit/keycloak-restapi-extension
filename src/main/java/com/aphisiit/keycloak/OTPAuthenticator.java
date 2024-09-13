package com.aphisiit.keycloak;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.OTPCredentialModel;
import org.keycloak.services.messages.Messages;

import jakarta.ws.rs.core.Response;

public class OTPAuthenticator implements Authenticator {
    
    private final Logger log = Logger.getLogger(this.getClass());

    @Override
    public void authenticate(AuthenticationFlowContext context) {

        UserModel user = context.getUser();

        log.info("user.credentialManager().isConfiguredFor(OTPCredentialModel.TYPE): " + user.credentialManager().isConfiguredFor(OTPCredentialModel.TYPE));

        log.info("OTPCredentialModel.TYPE.count(): " + user.credentialManager().getStoredCredentialsByTypeStream(OTPCredentialModel.TYPE).count());

        if (user.credentialManager().isConfiguredFor(OTPCredentialModel.TYPE)) {

            log.info("context.getAuthenticationSession().getAction(): " + context.getAuthenticationSession().getAction());
            log.info("context.getAuthenticationSession().getParentSession(): " + context.getAuthenticationSession().getParentSession());
            log.info("context.getAuthenticationSession().getTabId(): " + context.getAuthenticationSession().getTabId());

            Response challenge = Response.status(400) 
                // .entity("{\"error\": \"otp_required\", \"reference\": \"" + context.getAuthenticationSession().getTabId() + "\"}")
                .entity("{\"error\": \"otp_required\", \"reference\": \"" + context.getAuthenticationSession().getTabId() + "\"}")
                .type("application/json")
            .build();

            context.challenge(challenge);
        } else {
            context.success();
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // When the user submits the OTP
        UserModel user = context.getUser();
        String otp = context.getHttpRequest().getDecodedFormParameters().getFirst(OTPCredentialModel.TYPE);

        log.info("otp: " + otp);

        if (otp == null || otp.isEmpty()) {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                    context.form().setError(Messages.MISSING_TOTP).createErrorPage(Response.Status.BAD_REQUEST));
            return;
        }

        // UserCredentialModel.otp(OTPCredentialModel.TYPE, otp);

        // user.credentialManager().isValid(UserCredentialModel.otp(OTPCredentialModel.TYPE, otp));
        // context.
        boolean validOtp = user.credentialManager().isValid(UserCredentialModel.otp(OTPCredentialModel.TYPE, otp));

        // boolean isValidOtp = context.getSession().userCredentialManager().isValid(
        //         context.getRealm(),
        //         context.getUser(),
        //         UserCredentialModel.otp(otp)
        // );

        if (validOtp) {
            context.success();
        } else {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS,
                    context.form().setError(Messages.INVALID_TOTP).createErrorPage(Response.Status.BAD_REQUEST));
        }
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        // return false;
        return user.credentialManager().isConfiguredFor(OTPCredentialModel.TYPE);
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }
}
