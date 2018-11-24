package br.ufma.lsdi.converter;

/**
 * Created by Maykon Deykon on 05/04/2018.
 */
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.text.Normalizer;

@FacesConverter(value = "textEditorConverter", forClass = String.class)
public class TextEditorConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return value;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null || value.toString().isEmpty()) {
            return null;
        } else {
            return value.toString();
        }
    }
}