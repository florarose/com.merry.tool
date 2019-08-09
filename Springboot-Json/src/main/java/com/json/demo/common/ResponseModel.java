package com.json.demo.common;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;


public class ResponseModel<T> implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int code;
  private String message;

  @JsonView(value = View.Base.class )
  private T data;

  public ResponseModel(int code, String message, T responseData) {
    this.code = code;
    this.message= message;
    this.data = responseData;
  }


  public ResponseModel(ResponseCode responseCode, T data) {
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
    this.data = data;
  }
  
  public static <T> ResponseModel<T> SUCCESS(T t){
	  return new ResponseModel<T>(ResponseCode.OK, t);
  }

}
