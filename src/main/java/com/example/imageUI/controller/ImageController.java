package com.example.imageUI.controller;

import com.example.imageUI.domain.Image;
import com.example.imageUI.dto.ImageDto;
import com.example.imageUI.dto.request.CreateImageRequest;
import com.example.imageUI.exceptions.ImageNotFoundExceptions;
import com.example.imageUI.exceptions.ImageNotFoundRestException;
import com.example.imageUI.mapper.ImageMapper;
import com.example.imageUI.service.Imp.ImageServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/images")
public class ImageController {
    public static final String IMAGE_NOT_FOUND = "Image not found";
    private final ImageServiceImp imageServiceImp;
    private final ImageMapper mapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public Iterable<ImageDto> findAll() {
        return mapper.map(imageServiceImp.findAll());
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ImageDto createImage(@RequestParam("file") MultipartFile file) {
        return mapper.map(imageServiceImp.createImage(file));
    }

    @GetMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.OK)
    public ImageDto findById(@PathVariable("uuid") final UUID uuid) {
        Image image = imageServiceImp.findByUuid(uuid).orElseThrow(() -> new ImageNotFoundRestException(IMAGE_NOT_FOUND));
        return mapper.map(image);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable("uuid") final UUID uuid) {
        try {
            imageServiceImp.deleteImage(uuid);
        } catch (ImageNotFoundExceptions e) {
            throw new ImageNotFoundRestException(e.getMessage());
        }
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(code = HttpStatus.OK)
    public ImageDto updateImage(@Valid @RequestBody final CreateImageRequest request,
                                @PathVariable("uuid") final UUID uuid) {
        Image image = imageServiceImp.updateImage(request, uuid).orElseThrow(() -> new ImageNotFoundRestException(IMAGE_NOT_FOUND));
        return mapper.map(image);
    }
}
