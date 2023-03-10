package com.cruise.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Custom tag for displaying current data.
 *
 * @author Vasyl Utrysko.
 * @version 1.0
 */
public class CurrentDateTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
       try {
           pageContext.getOut().write(String.valueOf(LocalDate.now()));
       }catch (IOException e){
           throw new JspException(e.getMessage());
       }
       return SKIP_BODY;
    }
}
