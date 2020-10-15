package com.omarghorbel.storecrud.controller;

import com.omarghorbel.storecrud.dao.ImageAvantLivraisonRepository;
import com.omarghorbel.storecrud.entities.ImageAvantLivraison;
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
@RequestMapping(path = "imageuploadavlivraison")
public class ImageAvantLivraisonUploadController {
    @Autowired
    ImageAvantLivraisonRepository imageAvantLivraisonRepository;

    @PostMapping("/uploadavlivraison")
//	(@RequestBody TicketForm ticketForm)
//	public picModel uplaodImage(@RequestParam("model") String model, @RequestParam(value = "imageFile") MultipartFile file) throws IOException {

    public ImageAvantLivraison uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        ImageAvantLivraison imgx = new ImageAvantLivraison(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));

        final ImageAvantLivraison img = imageAvantLivraisonRepository.save(imgx);
        return img;

    }

    @GetMapping(path = { "/get/{imageName}" })
    public ImageAvantLivraison getImage(@PathVariable("imageName") String imageName) throws IOException {

        final Optional<ImageAvantLivraison> retrievedImage = imageAvantLivraisonRepository.findByName(imageName);
        ImageAvantLivraison img = new ImageAvantLivraison( retrievedImage.get().getName(), retrievedImage.get().getType(),
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
