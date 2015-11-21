package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value="emailValidatation")
public class EmailValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String emailAddress = (String)value;
        
        HtmlInputText htmlInputText = (HtmlInputText)component;
        
        if (!emailAddress.matches("[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z0-9]+")){
            FacesMessage facesMessage =  new FacesMessage(htmlInputText.getLabel()+": email format is incorrect.");
            throw new ValidatorException(facesMessage);
        }
    }
}