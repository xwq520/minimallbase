package com.minimall.boilerplate.common;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by lemon on 2018/3/23.
 */
public final class MailHelper {

    private String mailServer;

    private String mailUser;

    private String mailPwd;

    public MailHelper(){

    }
    public MailHelper(String mailServer, String mailUser, String mailPwd) {
        this.mailServer = mailServer;
        this.mailUser = mailUser;
        this.mailPwd = mailPwd;
    }

    public boolean sendMailMessage(String to, String subject, String content) {
        try{
            // 配置发送邮件的环境属性
            final Properties props = new Properties();
            /*
             * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
             * mail.user / mail.from
             */
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", mailServer);
            // 发件人的账号
            props.put("mail.user", mailUser);
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", mailPwd);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            InternetAddress form = new InternetAddress(
                    props.getProperty("mail.user"));
            message.setFrom(form);

            // 设置收件人
            message.setRecipients(Message.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件的内容体
            message.setContent(content, "text/plain;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public String getMailServer() {
        return mailServer;
    }

    public void setMailServer(String mailServer) {
        this.mailServer = mailServer;
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    public String getMailPwd() {
        return mailPwd;
    }

    public void setMailPwd(String mailPwd) {
        this.mailPwd = mailPwd;
    }
}
