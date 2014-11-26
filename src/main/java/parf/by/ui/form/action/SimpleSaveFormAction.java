package parf.by.ui.form.action;

import info.magnolia.ui.form.action.SaveFormAction;
import info.magnolia.ui.form.action.SaveFormActionDefinition;

import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.form.EditorCallback;
import info.magnolia.ui.form.EditorValidator;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Kiryl_Parfiankou on 11/26/2014.
 */
public class SimpleSaveFormAction extends SaveFormAction {

    private static final Logger log = LoggerFactory.getLogger(SimpleSaveFormAction.class);

    public SimpleSaveFormAction( SaveFormActionDefinition definition, JcrNodeAdapter item, EditorCallback callback, EditorValidator validator ) {
        super( definition,item, callback, validator );
    }

    public void execute() throws ActionExecutionException {
        // First Validate
        validator.showValidation(true);
        if ( validator.isValid() ) {
            try {
                final Node node = item.getJcrItem();
                setNodeName(node, item);
                node.getSession().save();
            } catch ( final RepositoryException e ) {
                throw new ActionExecutionException(e);
            }
            callback.onSuccess(getDefinition().getName());
        } else {
            log.info("Validation error(s) occurred. No save performed.");
        }
    }
}
