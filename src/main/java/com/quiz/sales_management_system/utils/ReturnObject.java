package com.quiz.sales_management_system.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnObject<T> {
    String message;
    Boolean status;
    T data;

}
