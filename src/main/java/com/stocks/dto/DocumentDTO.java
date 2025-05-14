package com.stocks.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DocumentDTO {
    private Long id;
    private String documentName;
    private String fileType; // image/pdf
    private LocalDate uploadedDate;
}