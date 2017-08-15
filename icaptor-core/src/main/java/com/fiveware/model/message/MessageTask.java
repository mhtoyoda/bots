package com.fiveware.model.message;

<<<<<<< HEAD
import com.fiveware.messaging.TypeMessage;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.io.Serializable;

=======
import java.io.Serializable;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

>>>>>>> 564bcc835f6601382c022a18bfbff6a43fa008c7
@AutoProperty
public class MessageTask implements Serializable{

	private String nameQueueTask;
	private String botName;
<<<<<<< HEAD
	private TypeMessage typeMessage;

=======
>>>>>>> 564bcc835f6601382c022a18bfbff6a43fa008c7

	public MessageTask(String nameQueueTask, String botName) {
		super();
		this.nameQueueTask = nameQueueTask;
		this.botName = botName;
	}

	public String getNameQueueTask() {
		return nameQueueTask;
	}
	
	public String getBotName() {
		return botName;
	}
<<<<<<< HEAD

	public TypeMessage getTypeMessage() {
		return typeMessage;
	}



=======
	
>>>>>>> 564bcc835f6601382c022a18bfbff6a43fa008c7
	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}
}