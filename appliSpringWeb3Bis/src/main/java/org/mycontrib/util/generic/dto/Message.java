package org.mycontrib.util.generic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class Message {
		private String message;
		//....

		public Message(String message) {
			super();
			this.message = message;
		}
}
