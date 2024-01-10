package com.dong.apipassenger.intercepter;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.dong.internalcommon.dto.ResponseResult;
import com.dong.internalcommon.util.JwtUtils;

import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");
        try{
            JwtUtils.parseToken(token);
        }catch (SignatureVerificationException signatureVerificationException){
            resultString = "token sign error";
            result = false;
        }catch (TokenExpiredException tokenExpiredException){
            resultString = "token time out";
            result = false;
        }catch (AlgorithmMismatchException algorithmMismatchException){
            resultString = "token AlgorithmMismatchException";
            result = false;
        }catch (Exception exception){
            resultString = "token invalid";
            result = false;
        }

        if(!result){
            PrintWriter pw = response.getWriter();
            pw.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;

    }
}
