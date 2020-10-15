package com.omarghorbel.storecrud.controller;

import com.omarghorbel.storecrud.dao.ImageRetourRepository;
import com.omarghorbel.storecrud.entities.ImageRetour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@CrossOrigin(origins = "http://localhost:4300")
@RequestMapping(path = "imageretour")
public class ImageRetourUploadController {
    @Autowired
    ImageRetourRepository imageRetourRepository;

    @PostMapping("/uploadretour")
//	(@RequestBody TicketForm ticketForm)
//	public picModel uplaodImage(@RequestParam("model") String model, @RequestParam(value = "imageFile") MultipartFile file) throws IOException {

    public ImageRetour uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        ImageRetour imgx = new ImageRetour(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));

        final ImageRetour img = imageRetourRepository.save(imgx);
        return img;

    }

    @GetMapping(path = { "/get/{imageName}" })
    public ImageRetour getImage(@PathVariable("imageName") String imageName) throws IOException {

        final Optional<ImageRetour> retrievedImage = imageRetourRepository.findByName(imageName);
        ImageRetour img = new ImageRetour( retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }



}
