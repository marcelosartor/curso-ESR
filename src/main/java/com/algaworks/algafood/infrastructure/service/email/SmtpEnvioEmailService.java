package com.algaworks.algafood.infrastructure.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.core.email.EmailProperties;
import com.algaworks.algafood.domain.service.EnvioEmailService;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {
		
		 try {
			 	System.out.println("SmtpEnvioEmailService");
		        MimeMessage mimeMessage = criarMimeMessage(mensagem);
		        
		        mailSender.send(mimeMessage);
		    } catch (Exception e) {
		        throw new EmailException("Não foi possível enviar e-mail", e);
		    }
		 
	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
	    String corpo = processarTemplate(mensagem);
	    
	    MimeMessage mimeMessage = mailSender.createMimeMessage();
	    
	    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
	    helper.setFrom(emailProperties.getRemetente());
	    helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
	    helper.setSubject(mensagem.getAssunto());
	    helper.setText(corpo, true);
	    
	    return mimeMessage;
	}
	protected String processarTemplate(Mensagem mensagem) {
		try {
			System.out.println(mensagem.getCorpo());
			
			
			Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			
			String body = FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
			              
			
			System.out.println(body);
			return body;
			
			//var template = freemarkerConfig.getTemplate(mensagem.getCorpo());
			//return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Não foi possivel montar o template do email",e);
		}

	}
	
}
