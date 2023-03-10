package com.example.imageUI.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ImageDto {
    int id;
    UUID uuid;
    String name;
    LocalDateTime created;
    LocalDateTime modified;
}
