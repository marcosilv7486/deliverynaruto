package com.marcosilv7.narutodelivery.configuration.messages;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    public MessageUtil(ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String findByCode(String code){
        try {
            return messageSource.getMessage(code,null, LocaleContextHolder.getLocale());
        }catch (NoSuchMessageException e){
            logger.warn("No se encontro el codigo del mensaje para : "+code);
            return code;
        }
    }
}
