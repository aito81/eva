package py.com.tipcsa.eva.event;

import com.vaadin.ui.UI;

public class LogoutEvent {
	public LogoutEvent(){
		UI.getCurrent().getPage().setUriFragment("");
	}
}
