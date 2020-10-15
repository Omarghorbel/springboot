package com.omarghorbel.storecrud.controller;

import com.omarghorbel.storecrud.dao.ProductRepository;
import com.omarghorbel.storecrud.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.io.ByteArrayOutputStream;

import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "image")
public class ProductUploadController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/upload")
//	(@RequestBody TicketForm ticketForm)
//	public picModel uplaodImage(@RequestParam("model") String model, @RequestParam(value = "imageFile") MultipartFile file) throws IOException {

    public Product uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {

        Product imgx = new Product(file.getOriginalFilename(), file.getContentType(),
                compressBytes(file.getBytes()));

        final Product img = productRepository.save(imgx);
        return img;

    }

    @GetMapping(path = { "/get/{imageName}" })
    public Product getImage(@PathVariable("imageName") String imageName) throws IOException {

        final Optional<Product> retrievedImage = productRepository.findByName(imageName);
        Product img = new Product(retrievedImage.get().getId(), retrievedImage.get().getName(), retrievedImage.get().getType(),
                decompressBytes(retrievedImage.get().getPicByte()));
        return img;
    }
    @GetMapping(path = { "/id/{id}" })
    public Product getId(@PathVariable("id") Long id) throws IOException {

        final Optional<Product> retrievedImage = productRepository.findById(id);
        Product img = new Product( retrievedImage.get().getId(),retrievedImage.get().getName(), retrievedImage.get().getType(),
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
