package com.workintech.spring17challenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiErrorResponse {
    private int status;  //http status code
    private String message;  //hata mesajı
    private long timestamp; //hatanın oluştuğu zaman dilimi
}
