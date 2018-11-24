package br.ufma.lsdi.configuration.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * Created by Artur Veiga on 19/07/2017.
 */

@Component
public class UserLogged implements Serializable {
    private Object userLogged;

    transient Logger logger = LoggerFactory.getLogger(UserLogged.class);


}
