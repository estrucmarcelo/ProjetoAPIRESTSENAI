package br.com.senai.exception;

import lombok.Data;
// Classe que representa o formato json de mensagem de erro
@Data
public class StandardError {
	
	private Long timeStamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
	
	public StandardError() {
		super();
	}

	public StandardError(Long timeStamp, Integer status, String error, String message, String path) {
		super();
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}
}
