/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.api;

import com.google.gson.Gson;
import truongtn.dto.UserInfo;
import truongtn.utils.NetUtils;

/**
 *
 * @author truongtn
 */
public class APIWrapper {

    private static String appID = "548837722387883";
    private static String appSecret = "0c64733d3b1014c096c984522b274522";
    private static String redirectUrl = "http://localhost:8080/YellowMoonShop/FaceBookController";
    private String accessToken;
    private Gson gson;

    public APIWrapper() {
        this.gson = new Gson();
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public static String getDialogLink() {
        String dialogLink = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s";

        return String.format(dialogLink, appID, redirectUrl);
    }

    public String getAccessToken(String code) {
        String accessTokenLink = "https://graph.facebook.com/oauth/access_token?"
                + "client_id=%s"
                + "&client_secret=%s"
                + "&redirect_uri=%s"
                + "&code=%s";
        accessTokenLink = String.format(accessTokenLink, appID, appSecret, redirectUrl, code);
        String result = NetUtils.GetResult(accessTokenLink);
        String token = result.substring(result.indexOf(":") + 2, result.indexOf(",") - 1);
        return token;
    }

    public UserInfo getUserInfo() {
        String infoUrl = "https://graph.facebook.com/me?fields=email,name,id&access_token=%s";
        infoUrl = String.format(infoUrl, this.accessToken);

        String result = NetUtils.GetResult(infoUrl);
        UserInfo userInfo = gson.fromJson(result, UserInfo.class);

        return userInfo;
    }
}
