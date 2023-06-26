package py.com.tipcsa.eva.util;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;

public class PageTitleUpdater implements ViewChangeListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

        View view = event.getNewView();
        ViewConfig viewConfig = view.getClass().getAnnotation(ViewConfig.class);

        if (viewConfig != null) {
            Page.getCurrent().setTitle(viewConfig.displayName());
        }

    }
}
