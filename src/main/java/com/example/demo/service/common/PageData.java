package com.example.demo.service.common;

import lombok.Data;

@Data
public class PageData {
  long itemTotal;
  long pageSize;
  Object list;
}
