package com.cirtual.data;

import java.sql.Timestamp;

public interface MessageProjection {
	public int getIdmessages();
	public String getMessageText();
	public Timestamp getSentAt();
}
