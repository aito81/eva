package py.com.tipcsa.eva.event;

import py.com.tipcsa.eva.entities.Usuario;

public class LoginEvent {
	private Usuario user;
	
	public LoginEvent(Usuario user){
		this.user = user;
	}
	
	public Usuario getUser(){
		return user;
	}

}
