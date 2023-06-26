package py.com.tipcsa.eva.event;

import py.com.tipcsa.eva.EvaUI;

public class EventBus {

	public static void register(final Object listener){
		EvaUI.getEventBus().register(listener);
	}
	
	public static void unregister(final Object listener){
		EvaUI.getEventBus().unregister(listener);
	}
	
	public static void post(final Object listener){
		EvaUI.getEventBus().post(listener);
	}
}
