package com.mate.carpool.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class OAuthService {
  public String getKakaoAccessToken(String code) {
    String access_Token = "";
    String refresh_Token = "";
    String reqURL = "https://kauth.kakao.com/oauth/token";

    try {
      URL url = new URL(reqURL);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);

      String redirectUri = URLEncoder.encode("redirect_uri=http://localhost:8080/api/oauth/kakao_login", "UTF-8");

      // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
      BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
      StringBuilder sb = new StringBuilder();
      sb.append("grant_type=authorization_code");
      sb.append("&client_id=8763097c83420044eeea901b962072ab");
      sb.append("&client_secret=QQP9e4kuegEA1ZQLUSDFINBknLcDoL8R");
      sb.append("&" + redirectUri);
      sb.append("&code=" + code);
      bw.write(sb.toString());
      bw.flush();

      // 결과 코드가 200이라면 성공
      int responseCode = conn.getResponseCode();
      System.out.println("responseCode : " + responseCode);

      // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
      BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      String line = "";
      String result = "";

      while ((line = br.readLine()) != null) {
        result += line;
      }
      System.out.println("response body : " + result);

      // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
      JsonElement element = JsonParser.parseString(result);

      access_Token = element.getAsJsonObject().get("access_token").getAsString();
      refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

      System.out.println("access_token : " + access_Token);
      System.out.println("refresh_token : " + refresh_Token);

      br.close();
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return access_Token;
  }
}