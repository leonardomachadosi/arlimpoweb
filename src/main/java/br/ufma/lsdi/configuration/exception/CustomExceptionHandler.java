package br.ufma.lsdi.configuration.exception;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;
import java.util.Map;

public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context
                    = (ExceptionQueuedEventContext) event.getSource();

            // get the exception from context
            Throwable t = context.getException();

            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            //here you do what ever you want with exception
            try {

                //log error ?
                //log.log(Level.SEVERE, "Critical Exception!", t);
                if (t instanceof ViewExpiredException) {
                    requestMap.put("javax.servlet.error.message", "Session expired, try again!");
                    String errorPageLocation = "/viewExpired";
                    fc.setViewRoot(fc.getApplication().getViewHandler().createView(fc, errorPageLocation));
                    fc.getPartialViewContext().setRenderAll(true);
                    fc.renderResponse();
                } else {
                    //redirect error page
                    requestMap.put("javax.servlet.error.message", t.getMessage());
                    nav.handleNavigation(fc, null, "/viewExpired");
                }

                fc.renderResponse();
                // remove the comment below if you want to report the error in a jsf error message
                //JsfUtil.addErrorMessage(t.getMessage());
            } finally {
                //remove it from queue
                i.remove();
            }
        }
        //parent hanle
        getWrapped().handle();
    }
}