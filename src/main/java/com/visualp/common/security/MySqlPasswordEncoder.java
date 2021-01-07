package com.visualp.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import javax.persistence.AttributeConverter;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public class MySqlPasswordEncoder implements PasswordEncoder, AttributeConverter<String, String> {

    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            throw new NullPointerException();
        }

        byte[] bpara = new byte[rawPassword.length()];

        byte[] rethash;
        int i;
        for (i = 0; i < rawPassword.length(); i++)
            bpara[i] = (byte) (rawPassword.charAt(i) & 0xff);
        try {
            MessageDigest sha1er = MessageDigest.getInstance("SHA1");
            rethash = sha1er.digest(bpara); // stage1
            rethash = sha1er.digest(rethash); // stage2
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        StringBuffer r = new StringBuffer(41);
        r.append("*");
        for (i = 0; i < rethash.length; i++) {
            String x = Integer.toHexString(rethash[i] & 0xff).toUpperCase();
            if (x.length() < 2)
                r.append("0");
            r.append(x);
        }

        return r.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }

        return encodedPassword.equals(encode(rawPassword));
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        if (s != null) {
            return this.encode(s);
        } else {
            return null;
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s;
    }
}
